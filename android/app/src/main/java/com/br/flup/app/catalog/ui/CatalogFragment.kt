package com.br.flup.app.catalog.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.br.flup.app.R
import com.br.flup.app.catalog.viewmodel.CatalogViewModel
import com.br.flup.app.core.data.Outcome.*
import com.br.flup.app.core.extension.getViewModel
import com.br.flup.app.core.manager.SessionManager
import kotlinx.android.synthetic.main.catalog_fragment.*
import kotlinx.android.synthetic.main.core_activity.*

class CatalogFragment : Fragment() {

    private val vm by lazy {
        getViewModel { CatalogViewModel() }
    }

    private val navController by lazy {
        this.findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.catalog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomAppBar()
        setupBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        vm.fetchProducts()
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
            it.topAppBarLayout.visibility = View.VISIBLE
        }
    }

    private fun setupBinding() {
        vm.fetchProductsOutcome.observe(this, Observer { outcome ->
            when (outcome) {
                is Progress -> println("Loading")
                is Success -> println("Success")
                is Error -> println("Error")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
    }

}