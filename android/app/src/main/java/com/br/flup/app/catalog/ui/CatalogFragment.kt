package com.br.flup.app.catalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.flup.app.R
import com.br.flup.app.core.manager.SessionManager
import kotlinx.android.synthetic.main.catalog_fragment.*

class CatalogFragment : Fragment() {

    private val navController by lazy {
        this.findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.catalog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logoutButton.setOnClickListener {
            SessionManager.clearSession()
            navController.navigateUp()
        }
    }

}