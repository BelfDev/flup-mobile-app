package com.br.flup.app.core.data.remote

import com.br.flup.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("Token", "XPTO")
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private fun okHttpDefaultBuilder() : OkHttpClient.Builder  {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder
    }

    private fun retrofit(baseUrl: String, client: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()

    private val flupClient = okHttpDefaultBuilder()
        .addInterceptor(authInterceptor)
        .build()

    val flupApiService = retrofit(BuildConfig.BASE_URL_FLUP, flupClient).create(FlupApi::class.java)

}