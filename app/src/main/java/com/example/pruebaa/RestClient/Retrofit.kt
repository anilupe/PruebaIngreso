package com.example.pruebaa.RestClient

import com.example.pruebaa.Commons.Commons
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    //instanciar la clase commons donde se encuentra la url base
    val commons = Commons()

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(commons.urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}