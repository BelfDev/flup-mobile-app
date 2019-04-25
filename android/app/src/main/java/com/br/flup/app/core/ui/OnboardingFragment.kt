package com.br.flup.app.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.flup.app.R
import com.br.flup.app.core.manager.SessionManager

class OnboardingFragment : Fragment() {

    companion object {
        fun newInstance() = OnboardingFragment()
    }

    private val navController by lazy {
        this.findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.onboarding_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val destination = if (SessionManager.isAuthenticated)  R.id.action_onboarding_to_catalog else R.id.action_onboarding_to_auth
        navController.navigate(destination)
    }

}
