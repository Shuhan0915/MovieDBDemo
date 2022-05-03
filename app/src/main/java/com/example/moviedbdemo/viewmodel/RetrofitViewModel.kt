package com.example.moviedbdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.retrofit.MovieAPIClient
import com.example.moviedbdemo.retrofit.MovieAPIInterface
import com.example.moviedbdemo.utils.addAll
import kotlinx.coroutines.*

/*
    View model to help deal with web process
 */
class RetrofitViewModel(application: Application) : AndroidViewModel(application) {

    private var retrofitInterface: MovieAPIInterface = MovieAPIClient.getRetrofitService()
    private var page: Int = 0

    // latest movie list or search result
    var movieList: MutableLiveData<List<Movie>> = MutableLiveData()

    // search view suggestion list
    var suggestMovieList: MutableLiveData<List<String>> = MutableLiveData()

    // used to detect if this action is search movie or get latest movie
    var searchMode: Boolean = false

    // query to search movie
    var query: String = ""

    init {
        fetchMovieList()
        movieList.value = arrayListOf()
    }

    fun fetchMovieList() {
        page++
        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitInterface.getLatestReleasedMovie(
                page = page
            )
            if (response.isSuccessful) {
                movieList.addAll(response.body()?.results)
                Log.i("Row Response ", response.raw().toString())
                Log.i("Success body", response.body().toString())
            } else {
                Log.e("Error ", "Response failed")
                Log.e("Error ", response.toString())
            }
        }
    }

    // search movie by query and post to activity to display
    fun searchMovie() {
        searchMode = true
        page++
        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitInterface.searchMovie(
                page = page,
                query = query
            )
            if (response.isSuccessful) {
                movieList.addAll(response.body()?.results)
                Log.i("Row Response ", response.raw().toString())
                Log.i("Success ", response.body().toString())
            } else {
                Log.e("Error ", "Response failed")
                Log.e("Error ", response.toString())
            }
        }
    }

    // fetch suggestion movie list based on query
    fun fetchSuggestMovieList(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitInterface.searchMovie(
                page = page,
                query = query
            )
            if (response.isSuccessful) {
                val tempList: ArrayList<String> = arrayListOf()
                response.body()?.results?.map {
                    tempList.add(it.title)
                }
                suggestMovieList.postValue(tempList)
                Log.i("Row Response ", response.raw().toString())
                Log.i("Success ", response.body().toString())
            } else {
                Log.e("Error ", "Response failed")
                Log.e("Error ", response.toString())
            }
        }
    }

    fun resetViewModel(query: String = "") {
        page = 0
        this.query = query
        searchMode = false
        movieList.value = arrayListOf()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}