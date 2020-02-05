package com.example.gitlist.network.service

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ServiceFactory {

    fun <T> createService(klass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(klass)
    }

}