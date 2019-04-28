package com.br.flup.app.core.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.br.flup.app.R
import com.br.flup.app.authentication.model.Event
import com.br.flup.app.authentication.model.SignInFailure
import com.br.flup.app.authentication.model.SignInForm
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.data.Outcome.*
import com.br.flup.app.core.extension.getViewModel
import kotlinx.android.synthetic.main.activity_main.*

class CoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottomAppBar)
    }

}
