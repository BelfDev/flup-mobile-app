package com.br.flup.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.br.flup.app.authentication.data.SessionRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private lateinit var sessionRepository: SessionRepository

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionRepository = SessionRepository(compositeDisposable)
    }

    override fun onStop() {
        super.onStop()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
