package com.br.flup.app.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.br.flup.app.R
import kotlinx.android.synthetic.main.core_activity.*

class CoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.core_activity)
        setSupportActionBar(bottomAppBar)

        bottomAppBar.setNavigationOnClickListener {
            val bottomNavDrawerFragment = BottomSheetDrawerFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }
    }

}
