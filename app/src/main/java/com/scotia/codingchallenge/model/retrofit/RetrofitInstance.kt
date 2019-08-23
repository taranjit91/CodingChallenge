package com.scotia.codingchallenge.model.retrofit


import com.scotia.codingchallenge.utils.CommonConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CommonConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}