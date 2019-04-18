package com.br.flup.app.authentication.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.extension.getViewModel
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
        authenticationContainer.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
