package com.br.flup.app.authentication.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.extension.getViewModel
import com.transitionseverywhere.extra.Scale
import kotlinx.android.synthetic.main.auth_fragment.*


class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private val vm by lazy {
        getViewModel { AuthViewModel() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.br.flup.app.R.layout.auth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewListeners()
        // Use viewModel here
    }

    private fun setupViewListeners() {
        authContainer.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        authFormFAB.setOnClickListener { onFabClick() }
    }

    private fun onFabClick() {
        val transitionSet = TransitionSet()
            .addTransition(Scale(1.2f))
            .addTransition(Fade())
            .setInterpolator(FastOutLinearInInterpolator())
        TransitionManager.beginDelayedTransition(authContainer, transitionSet)
        authRearFormView.visibility = if (authRearFormView.isVisible) View.INVISIBLE else View.VISIBLE
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
