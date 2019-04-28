package com.br.flup.app.core.extension

import com.google.gson.Gson
import retrofit2.HttpException

private val gson = Gson()

fun <T> mapErrorBody(error: HttpException, type: Class<T>) =
    error.response().errorBody()?.let {
        gson.fromJson(it.string(), type)
    }