package com.br.flup.app.catalog.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.flup.app.R
import com.br.flup.app.core.manager.SessionManager
import kotlinx.android.synthetic.main.core_activity.*
import kotlinx.android.synthetic.main.catalog_fragment.*

class CatalogFragment : Fragment() {

    private val navController by lazy {
        this.findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.catalog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomAppBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logoutButton.setOnClickListener {
            SessionManager.clearSession()
            navController.navigateUp()
        }
    }

    private fun setupBottomAppBar() {
        val activity = (requireActivity() as AppCompatActivity)
        activity?.let {
            it.bottomAppBar?.visibility = View.VISIBLE
            it.mainFAB?.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
    }

}