package com.br.flup.app.authentication.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.*
import com.br.flup.app.R
import com.br.flup.app.authentication.ui.AuthFragment.AuthScene.EMPLOYEE
import com.br.flup.app.authentication.ui.AuthFragment.AuthScene.EVENT
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.extension.getViewModel
import com.google.android.material.card.MaterialCardView
import com.transitionseverywhere.extra.Scale
import kotlinx.android.synthetic.main.auth_employee_form_view.view.*
import kotlinx.android.synthetic.main.auth_fragment.*

class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private enum class AuthScene {
        EVENT, EMPLOYEE
    }

    private val vm by lazy {
        getViewModel { AuthViewModel() }
    }

    private lateinit var mEventFormScene: Scene
    private lateinit var mEmployeeFormScene: Scene

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        setupScenes()
        // Use viewModel here
    }

    private fun setupListeners() {
        authContainer.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        authFormFAB.setOnClickListener { onFABClick() }
    }

    private fun setupScenes() {
        mEventFormScene = Scene.getSceneForLayout(formRootScene, R.layout.auth_event_form_scene, requireContext())
        mEmployeeFormScene = Scene.getSceneForLayout(formRootScene, R.layout.auth_employee_form_scene, requireContext())
    }

    private fun onFABClick() {
        transitionToScene(EMPLOYEE)
    }

    private fun onEmployeeFormBackButtonClick() {
        transitionToScene(EVENT)
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun transitionToScene(scene: AuthScene) {
        val transitionSet = TransitionSet()
        transitionSet.interpolator = FastOutSlowInInterpolator()
        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

        when (scene) {
            EMPLOYEE -> {
                val eventForm = mEventFormScene.sceneRoot[0] as MaterialCardView

                transitionSet
                    .addTransition(Fade().addTarget(eventForm))
                    .addTransition(Scale(1.2f).addTarget(eventForm))
                    .addTransition(ChangeBounds())

                TransitionManager.go(mEmployeeFormScene, transitionSet)

                val employeeForm = mEmployeeFormScene.sceneRoot[0] as MaterialCardView
                val pureWhiteColor = ContextCompat.getColor(requireContext(), R.color.whitePure)
                employeeForm.setCardBackgroundColor(pureWhiteColor)
                employeeForm.content.visibility = View.VISIBLE
                employeeForm.backButton.setOnClickListener { onEmployeeFormBackButtonClick() }
            }
            EVENT -> {
                val employeeForm = mEmployeeFormScene.sceneRoot[0] as MaterialCardView
                employeeForm.backButton.setOnClickListener(null)

                transitionSet
                    .addTransition(Fade(Fade.MODE_OUT).addTarget(employeeForm))
                    .addTransition(Scale(0.8f).addTarget(employeeForm))
                    .addTransition(ChangeBounds())

                TransitionManager.go(mEventFormScene, transitionSet)
            }
        }
    }
}
