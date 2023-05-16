package com.alexander.acttest.di

import com.alexander.acttest.UserClient
import com.alexander.acttest.util.CONNECT_TIMEOUT
import com.alexander.acttest.util.READ_TIMEOUT
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object NetworkModule {

    private val client = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(client)
        .baseUrl("https://api.github.com/")
        .build()

    val userClient: UserClient = retrofit.create(UserClient::class.java)
}