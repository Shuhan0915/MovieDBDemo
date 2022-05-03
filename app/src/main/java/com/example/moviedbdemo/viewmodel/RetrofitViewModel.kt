package com.example.moviedbdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.retrofit.MovieAPIClient
import com.example.moviedbdemo.retrofit.MovieAPIInterface
import kotlinx.coroutines.*

/*
    View model to help deal with web process
 */
class RetrofitViewModel(application: Application) : AndroidViewModel(application) {

    private var retrofitInterface: MovieAPIInterface = MovieAPIClient.getRetrofitService()
    var movieList: MutableLiveData<List<Movie>> = MutableLiveData()

    init {
        fetchLatestMovieList()
    }

    private fun fetchLatestMovieList() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitInterface.getLatestReleasedMovie()
            if (response.isSuccessful) {
                movieList.postValue(response.body()?.results)
                Log.i("Row Response ", response.raw().toString())
                Log.i("Success ", response.body().toString())
            } else {
                Log.i("Error ", "Response failed")
                Log.i("Error ", response.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}