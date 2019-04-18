package com.belfortdev.hurbchallenge.core.extension

import com.br.flup.app.core.data.*
import com.br.flup.app.core.extension.mapErrorBody
import com.br.flup.app.core.model.DomainMappable
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Completable]
 * */
fun Completable.performOnBackOutOnMain(scheduler: Scheduler): Completable {
    return this.subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Flowable]
 * */
fun <T> Flowable<T>.performOnBackOutOnMain(scheduler: Scheduler): Flowable<T> {
    return this.subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread  for a [Single]
 * */
fun <T> Single<T>.performOnBackOutOnMain(scheduler: Scheduler): Single<T> {
    return this.subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Observable]
 * */
fun <T> Observable<T>.performOnBackOutOnMain(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
}

/**
 * Extension function to add a Disposable to a CompositeDisposable
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

/**
 * Extension function to subscribe on the background thread for a Flowable
 * */
fun <T> Flowable<T>.performOnBack(scheduler: Scheduler): Flowable<T> {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Completable
 * */
fun Completable.performOnBack(scheduler: Scheduler): Completable {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Observable
 * */
fun <T> Observable<T>.performOnBack(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to automatically transform the stream's type to R
 * */
fun <T : DomainMappable<R>, R> Single<T>.mapToDomain(): Single<R> = this.map { it.asDomain() }

/**
 * Extension function to intercept exceptions and replace them with custom declared ones
 * */
fun <T> Single<T>.mapNetworkErrors(): Single<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Single.error(NoNetworkException(error))
            is UnknownHostException -> Single.error(ServerUnreachableException(error))
            is HttpException -> Single.error(HttpCallFailureException(error))
            else -> Single.error(error)
        }
    }

/**
 * Extension function to take the stream of R, catch HttpException if it occurs, map body json
 * to domain class and combine those two cases into single stream of R
 * */
inline fun <reified T : R, R> Single<out R>.mapError(): Single<R> =
    this.map { it }
        .onErrorResumeNext { error ->
            if (error is HttpException && error.code() >= 400) {
                mapErrorBody(error, T::class.java)?.let {
                    Single.just(it)
                } ?: Single.error(IllegalStateException("Mapping http body failed!"))
            } else {
                Single.error(error)
            }
        }

/**
 * Extension function to simplify request retry
 * */
fun <T, R> Single<T>.retry(count: Int, action: (Throwable) -> Flowable<R>): Single<T> =
    this.retryWhen { errors ->
        errors.countItems().flatMap { item ->
            if (item.second < count) {
                action.invoke(item.first)
            } else {
                Flowable.error<T>(MaxRetriesExceededException(item.first))
            }
        }
    }

/**
 * Extension function to support "retry" extension count the number of errors
 * */
fun <T> Flowable<T>.countItems(): Flowable<Pair<T, Int>> =
    Flowable.zip(
        this,
        Flowable.range(1, Int.MAX_VALUE),
        BiFunction { item, count -> Pair(item, count) }
    )