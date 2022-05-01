package com.example.moviedbdemo.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIClient {
    companion object RetrofitManager {
        private val BASE_URL = "https://api.themoviedb.org/3/"
        fun getRetrofitService(): MovieAPIInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MovieAPIInterface::class.java)
        }
    }
}