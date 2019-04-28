package com.br.flup.app.core.data

sealed class Outcome<T> {
    data class Progress<T>(var loading: Boolean) : Outcome<T>()
    data class Success<T>(var data: T) : Outcome<T>()
    data class Failure<T>(var data: T) : Outcome<T>()
    data class Error<T>(val e: Throwable) : Outcome<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): Outcome<T> = Progress(isLoading)

        fun <T> success(data: T): Outcome<T> = Success(data)

        fun <T> failure(data: T): Outcome<T> = Failure(data)

        fun <T> error(e: Throwable): Outcome<T> = Error(e)
    }
}
