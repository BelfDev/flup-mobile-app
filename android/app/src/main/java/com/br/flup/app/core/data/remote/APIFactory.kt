package com.br.flup.app.core.data.remote

import com.br.flup.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIFactory {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .addHeader("Authorization", "Bearer " + "INSERT-TOKEN")
            .build()

        chain.proceed(newRequest)
    }

    private fun okHttpDefaultBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder
    }

    private fun retrofit(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()

    private val flupClient = okHttpDefaultBuilder()
        .addInterceptor(authInterceptor)
        .build()

    /**
     * Services
     */

    val flupAPIService: FlupAPI = retrofit(BuildConfig.BASE_URL_FLUP, flupClient).create(FlupAPI::class.java)

}
