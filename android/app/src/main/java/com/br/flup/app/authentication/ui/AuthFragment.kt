package com.br.flup.app.authentication.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.br.flup.app.R
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.extension.getViewModel
import com.transitionseverywhere.extra.Scale
import kotlinx.android.synthetic.main.auth_event_form_scene.*
import kotlinx.android.synthetic.main.auth_fragment.*


class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private val vm by lazy {
        getViewModel { AuthViewModel() }
    }

    private lateinit var mFormEventScene: Scene
    private lateinit var mFormEmployeeScene: Scene

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewListeners()
        setupScenes()
        // Use viewModel here
    }

    private fun setupViewListeners() {
        authContainer.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        authFormFAB.setOnClickListener { onFabClick() }
    }

    private fun setupScenes() {
        mFormEventScene = Scene(formRootScene)
        mFormEmployeeScene = Scene.getSceneForLayout(formRootScene, R.layout.auth_employee_form_scene, requireContext())
    }

    private fun onFabClick() {
        animateFormTransition()
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun animateFormTransition() {
        val transitionSet = TransitionSet()
        val scaleTransition = Scale(1.2f).addTarget(authEventFormView)
        val fadeTransition = Fade().addTarget(authEventFormView)

        transitionSet
            .addTransition(fadeTransition)
            .addTransition(scaleTransition)
        transitionSet.interpolator = FastOutLinearInInterpolator()
        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

        TransitionManager.go(mFormEmployeeScene, transitionSet)
    }

}
