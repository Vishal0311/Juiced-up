package com.example.juiced_up.api.service

import com.example.juiced_up.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: JuicedUpApi by lazy {
        retrofit.create(JuicedUpApi::class.java)
    }

}