package com.br.flup.app.authentication.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.br.flup.app.R
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.extension.getViewModel
import com.google.android.material.card.MaterialCardView
import com.transitionseverywhere.extra.Scale
import kotlinx.android.synthetic.main.auth_employee_form_view.view.*
import kotlinx.android.synthetic.main.auth_event_form_scene.*
import kotlinx.android.synthetic.main.auth_fragment.*


class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
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
        mEventFormScene = Scene(formRootScene)
        mEmployeeFormScene = Scene.getSceneForLayout(formRootScene, R.layout.auth_employee_form_scene, requireContext())
    }

    private fun onFABClick() {
        transitionToEmployeeScene()
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun transitionToEmployeeScene() {
        val transitionSet = TransitionSet()
        val scaleTransition = Scale(1.2f).addTarget(authEventFormView)
        val fadeTransition = Fade().addTarget(authEventFormView)

        transitionSet
            .addTransition(fadeTransition)
            .addTransition(scaleTransition)
        transitionSet.interpolator = FastOutLinearInInterpolator()
        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

        TransitionManager.go(mEmployeeFormScene, transitionSet)
        setupEmployeeFormView(isActive = true)
    }

    private fun transitionToEventScene() {
//        val transitionSet = TransitionSet()
//        val scaleTransition = Scale(0.8f).addTarget(authEventFormView)
//        val fadeTransition = Fade().addTarget(authEventFormView)
//
//        transitionSet
//            .addTransition(fadeTransition)
//            .addTransition(scaleTransition)
//        transitionSet.interpolator = FastOutLinearInInterpolator()
//        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

//        TransitionManager.go(mEmployeeFormScene, transitionSet)
        TransitionManager.go(mEventFormScene)
//        setupEmployeeFormView(isActive = true)
    }

    private fun setupEmployeeFormView(isActive: Boolean) {
        val employeeForm = authEmployeeFormView as MaterialCardView
        if (isActive) {
            val pureWhiteColor = ContextCompat.getColor(requireContext(), R.color.whitePure)
            employeeForm.setCardBackgroundColor(pureWhiteColor)
            employeeForm.content.visibility = View.VISIBLE
        }
    }

}
