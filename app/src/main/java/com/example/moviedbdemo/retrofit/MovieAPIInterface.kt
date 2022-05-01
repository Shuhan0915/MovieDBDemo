package com.example.moviedbdemo.retrofit

import com.example.moviedbdemo.BuildConfig
import com.example.moviedbdemo.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
/*
    Retrofit interface: register movie API task
 */
interface MovieAPIInterface {

    @GET("movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") API_KEY: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String = "en-US"
    ): Response<MovieModel>
}