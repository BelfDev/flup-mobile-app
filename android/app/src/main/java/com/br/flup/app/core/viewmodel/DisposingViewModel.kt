package com.br.flup.app.core.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class DisposingViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}