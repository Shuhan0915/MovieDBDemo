package com.example.moviedbdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.retrofit.MovieAPIClient
import com.example.moviedbdemo.retrofit.MovieAPIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var retrofitInterface: MovieAPIInterface = MovieAPIClient.getRetrofitService()
    var movie: MutableLiveData<Movie>  = MutableLiveData<Movie>()

    fun fetchMovie(movieID:Int){
        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitInterface.getMovieByID(movieID)
            if (response.isSuccessful) {
                movie.postValue(response.body())
                Log.i("Row Response ", response.raw().toString())
                Log.i("Success ", response.body().toString())
            } else {
                Log.i("Error ", "Response failed")
                Log.i("Error ", response.toString())
            }
        }
    }

}