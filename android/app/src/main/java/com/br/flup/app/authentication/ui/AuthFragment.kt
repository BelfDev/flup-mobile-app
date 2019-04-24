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
import androidx.lifecycle.Observer
import androidx.transition.*
import com.br.flup.app.R
import com.br.flup.app.authentication.model.SignInFailure
import com.br.flup.app.authentication.ui.AuthFragment.SceneType.EMPLOYEE
import com.br.flup.app.authentication.ui.AuthFragment.SceneType.EVENT
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.data.Outcome.*
import com.br.flup.app.core.extension.getViewModel
import com.br.flup.app.databinding.AuthEmployeeFormViewBinding
import com.br.flup.app.databinding.AuthEventFormViewBinding
import com.google.android.material.card.MaterialCardView
import com.transitionseverywhere.extra.Scale
import kotlinx.android.synthetic.main.auth_employee_form_view.view.*
import kotlinx.android.synthetic.main.auth_employee_form_view.view.content
import kotlinx.android.synthetic.main.auth_error_view.view.*
import kotlinx.android.synthetic.main.auth_event_form_scene.*
import kotlinx.android.synthetic.main.auth_event_form_view.view.*
import kotlinx.android.synthetic.main.auth_fragment.*


class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private enum class SceneType {
        EVENT, EMPLOYEE
    }

    private var mCurrentSceneType: SceneType = EVENT
    private lateinit var mEventFormScene: Scene
    private lateinit var mEmployeeFormScene: Scene

    private val vm by lazy {
        getViewModel { AuthViewModel() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupContainerListeners()
        setupScenes()
        setupBinding()
    }

    private fun setupContainerListeners() {
        authContainer.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        authFormFAB.setOnClickListener { onFABClick() }
    }

    private fun setupScenes() {
        mEventFormScene = Scene.getSceneForLayout(formRootScene, R.layout.auth_event_form_scene, requireContext())
        val eventForm = mEventFormScene.sceneRoot[1]
        val eventFormBinding = AuthEventFormViewBinding.bind(eventForm)
        eventFormBinding.vm = vm
        eventForm.retryButton.setOnClickListener { onRetryButtonClick() }

        mEmployeeFormScene = Scene.getSceneForLayout(formRootScene, R.layout.auth_employee_form_scene, requireContext())
    }

    private fun setupBinding() {
        vm.signInOutcome.observe(this, Observer { outcome ->
            when (outcome) {
                is Progress -> vm.isLoading.set(outcome.loading)
                is Success -> {
                    vm.handleSuccessfulSignIn(outcome.data)
                    transitionBetweenScenes()
                }
                is Failure -> {
                    val failure = outcome.data as SignInFailure
                    setErrorFeedback(failure.errorMessage)
                    showErrorView()
                }
                is Error -> showErrorView()
            }
        })
    }

    private fun onFABClick() {
        when (mCurrentSceneType) {
            EVENT -> vm.signInEvent()
            EMPLOYEE -> vm.signInEmployee()
        }
    }

    private fun onFormBackButtonClick() {
        vm.resetForm()
        transitionBetweenScenes()
    }

    private fun onRetryButtonClick() {
        vm.isFailure.set(false)
        authFormFAB.visibility = View.VISIBLE
        val defaultErrorFeedback = context?.resources?.getString(R.string.default_error_feedback)
        defaultErrorFeedback?.let { setErrorFeedback(it) }
    }

    private fun transitionBetweenScenes() {
        val transitionSet = TransitionSet()
        transitionSet.interpolator = FastOutSlowInInterpolator()
        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

        when (mCurrentSceneType) {
            EVENT -> {
                authFormFAB.setImageResource(R.drawable.ic_done)
                val eventForm = mEventFormScene.sceneRoot[1] as MaterialCardView
                eventForm.eventErrorView.retryButton.setOnClickListener(null)

                transitionSet
                    .addTransition(Fade().addTarget(eventForm))
                    .addTransition(Scale(1.2f).addTarget(eventForm))
                    .addTransition(ChangeBounds())


                TransitionManager.go(mEmployeeFormScene, transitionSet)
                activateEmployeeForm()
                mCurrentSceneType = EMPLOYEE
            }
            EMPLOYEE -> {
                authFormFAB.setImageResource(R.drawable.ic_arrow_forward)
                val employeeForm = mEmployeeFormScene.sceneRoot[0] as MaterialCardView
                employeeForm.backButton.setOnClickListener(null)
                employeeForm.employeeErrorView.retryButton.setOnClickListener(null)

                transitionSet
                    .addTransition(Fade(Fade.MODE_OUT).addTarget(employeeForm))
                    .addTransition(Scale(0.8f).addTarget(employeeForm))
                    .addTransition(ChangeBounds())

                TransitionManager.go(mEventFormScene, transitionSet)
                activateEventForm()
                mCurrentSceneType = EVENT
            }
        }
    }

    private fun activateEmployeeForm() {
        val employeeForm = mEmployeeFormScene.sceneRoot[0] as MaterialCardView
        val binding = AuthEmployeeFormViewBinding.bind(employeeForm)
        binding.vm = vm

        val pureWhiteColor = ContextCompat.getColor(requireContext(), R.color.whitePure)
        employeeForm.setCardBackgroundColor(pureWhiteColor)
        employeeForm.content.visibility = View.VISIBLE
        employeeForm.backButton.setOnClickListener { onFormBackButtonClick() }
        employeeForm.employeeErrorView.retryButton.setOnClickListener { onRetryButtonClick() }
    }

    private fun activateEventForm() {
        val eventForm = mEventFormScene.sceneRoot[1]
        val eventFormBinding = AuthEventFormViewBinding.bind(eventForm)
        eventFormBinding.vm = vm
        eventForm.eventErrorView.retryButton.setOnClickListener { onRetryButtonClick() }
    }

    private fun showErrorView() {
        vm.isFailure.set(true)
        authFormFAB.visibility = View.GONE
    }

    private fun setErrorFeedback(feedback: String) {
        authEventFormView.eventErrorView.errorFeedback.text = feedback
        authEmployeeFormView.employeeErrorView.errorFeedback.text = feedback
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
