package com.br.flup.app.authentication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.br.flup.app.R
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.extension.getViewModel

class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private val vm by lazy {
        getViewModel { AuthViewModel() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Use viewModel here
    }

}
