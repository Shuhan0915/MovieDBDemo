package com.example.moviedbdemo.retrofit

import com.example.moviedbdemo.BuildConfig
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.model.MovieList
import com.example.moviedbdemo.retrofit.MovieAPIClient.RetrofitManager.RELEASE_DATE_DESC
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

/*
    Retrofit interface: register movie API task
 */
interface MovieAPIInterface {

    @GET("movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") API_KEY: String = BuildConfig.MOVIE_API_KEY
    ): Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieByID(
        @Path("movie_id") id: Int,
        @Query("api_key") API_KEY: String = BuildConfig.MOVIE_API_KEY
    ): Response<Movie>

    /*
         Get latest released movie list
         https://api.themoviedb.org/3/discover/movie?
         api_key=f4393d622fa954bb634e35c5330f9ae9
         &sort_by=release_date.desc
         &page=1
         &release_date.lte=2020-05-03
    */
    @GET("discover/movie")
    suspend fun getLatestReleasedMovie(
        @Query("api_key") API_KEY: String = BuildConfig.MOVIE_API_KEY,
        @Query("sort_by") sortBy: String = RELEASE_DATE_DESC,
        @Query("page") page: Int = 1,
        @Query("release_date") releaseDate: LocalDate = LocalDate.now(),
    ): Response<MovieList>

    /*
        search movie by query
        https://api.themoviedb.org/3/search/movie?
        api_key=f4393d622fa954bb634e35c5330f9ae9
        &query=fly
     */
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") API_KEY: String = BuildConfig.MOVIE_API_KEY,
        @Query("query") query: String = "",
        @Query("page") page: Int = 1
        ): Response<MovieList>
}