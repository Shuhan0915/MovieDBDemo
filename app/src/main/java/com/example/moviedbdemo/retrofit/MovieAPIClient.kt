package com.example.moviedbdemo.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    Retrofit web client to support to connect to themoviedb API
 */
class MovieAPIClient {
    companion object RetrofitManager {
        private val BASE_URL = "https://api.themoviedb.org/3/"
        const val RELEASE_DATE_DESC = "release_date.desc"
        const val POSTER_URL = "https://image.tmdb.org/t/p/w200"
        fun getRetrofitService(): MovieAPIInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MovieAPIInterface::class.java)
        }
    }
}