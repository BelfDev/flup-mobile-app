package com.belfortdev.hurbchallenge.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.br.flup.app.core.data.Outcome
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by belfortdev on 27/09/18.
 */

/**
 * Extension function to convert a Publish subject into a LiveData by subscribing to it.
 **/
fun <T> PublishSubject<T>.toLiveData(compositeDisposable: CompositeDisposable): LiveData<T> {
    val data = MutableLiveData<T>()
    compositeDisposable.add(this.subscribe { t: T -> data.value = t })
    return data
}

/**
 * Extension function to push a failed event with an exception to the observing outcome
 * */
fun <T> PublishSubject<Outcome<T>>.error(e: Throwable) {
    with(this) {
        loading(false)
        onNext(Outcome.error(e))
    }
}

/**
 * Extension function to push mapped failure event with data to the observing outcome
 * */
fun <T> PublishSubject<Outcome<T>>.failed(t: T) {
    with(this) {
        loading(false)
        onNext(Outcome.failure(t))
    }
}

/**
 * Extension function to push a success event with data to the observing outcome
 * */
fun <T> PublishSubject<Outcome<T>>.success(t: T) {
    with(this) {
        loading(false)
        onNext(Outcome.success(t))
    }
}

/**
 * Extension function to push the loading status to the observing outcome
 * */
fun <T> PublishSubject<Outcome<T>>.loading(isLoading: Boolean) {
    this.onNext(Outcome.loading(isLoading))
}
