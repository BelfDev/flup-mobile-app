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
import com.br.flup.app.authentication.model.Event
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
        setupListeners()
        setupScenes()
        setupBinding()
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
        val eventFormBinding = AuthEventFormViewBinding.bind(mEventFormScene.sceneRoot[1])
        eventFormBinding.vm = vm

        mEmployeeFormScene = Scene.getSceneForLayout(formRootScene, R.layout.auth_employee_form_scene, requireContext())
    }

    private fun setupBinding() {
        vm.signInEventOutcome.observe(this, Observer { outcome ->
            when (outcome) {
                is Progress -> vm.isLoading.set(outcome.loading)
                is Success -> {
                    vm.onSuccessfulEventSignIn(outcome.data as Event)
                    transitionToScene(EMPLOYEE)
                }
                is Failure -> {
                    println("FAILURE")
                    println(outcome.data)
                }
                is Error -> println("ERROR")
            }
        })

        vm.signInEmployeeOutcome.observe(this, Observer { outcome ->
            when (outcome) {
                is Progress -> println("PROGRESS")
                is Success -> println("SUCCESS")
                is Failure -> println("FAILURE")
                is Error -> println("ERROR")
            }
        })
    }

    private fun onFABClick() {
        println(vm.form.identifier)
        println(vm.form.password)
        when (mCurrentSceneType) {
            EVENT -> vm.signInEvent()
            EMPLOYEE -> {
                println("DONE!")
            }
        }
    }

    private fun onFormBackButtonClick() {
        transitionToScene(EVENT)
        vm.resetForm()
    }

    private fun transitionToScene(sceneType: SceneType) {
        mCurrentSceneType = sceneType
        val transitionSet = TransitionSet()
        transitionSet.interpolator = FastOutSlowInInterpolator()
        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

        when (sceneType) {
            EMPLOYEE -> {
                authFormFAB.setImageResource(R.drawable.ic_done)
                val eventForm = mEventFormScene.sceneRoot[0] as MaterialCardView

                transitionSet
                    .addTransition(Fade().addTarget(eventForm))
                    .addTransition(Scale(1.2f).addTarget(eventForm))
                    .addTransition(ChangeBounds())


                TransitionManager.go(mEmployeeFormScene, transitionSet)
                activateEmployeeForm()
            }
            EVENT -> {
                authFormFAB.setImageResource(R.drawable.ic_arrow_forward)
                val employeeForm = mEmployeeFormScene.sceneRoot[0] as MaterialCardView
                employeeForm.backButton.setOnClickListener(null)

                transitionSet
                    .addTransition(Fade(Fade.MODE_OUT).addTarget(employeeForm))
                    .addTransition(Scale(0.8f).addTarget(employeeForm))
                    .addTransition(ChangeBounds())

                TransitionManager.go(mEventFormScene, transitionSet)
                val eventFormBinding = AuthEventFormViewBinding.bind(mEventFormScene.sceneRoot[1])
                eventFormBinding.vm = vm
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
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
