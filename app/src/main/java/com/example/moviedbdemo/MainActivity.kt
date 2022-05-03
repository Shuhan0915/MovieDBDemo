package com.example.moviedbdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedbdemo.adapter.MovieListAdapter
import com.example.moviedbdemo.databinding.ActivityMainBinding
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.retrofit.MovieAPIClient
import com.example.moviedbdemo.retrofit.MovieAPIInterface
import com.example.moviedbdemo.viewmodel.RetrofitViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var movieListAdapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = activityMainBinding.root
        setContentView(view)
        val retrofitViewModel: RetrofitViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
                RetrofitViewModel::class.java
            )
        initMovieList()
        retrofitViewModel.movieList.observe(this, Observer { movieList ->
            movieList?.let {
                movieListAdapter.addResults(it as MutableList<Movie>)
            }
        })
    }

    private fun initMovieList() {
        movieListAdapter = MovieListAdapter(this)
        activityMainBinding.recyclerViewMovieList.adapter = movieListAdapter
        activityMainBinding.recyclerViewMovieList.layoutManager = GridLayoutManager(this, 2)
    }

}