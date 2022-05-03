package com.example.moviedbdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbdemo.adapter.MovieListAdapter
import com.example.moviedbdemo.databinding.ActivityMainBinding
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.viewmodel.RetrofitViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var retrofitViewModel: RetrofitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = activityMainBinding.root
        setContentView(view)
        retrofitViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
                RetrofitViewModel::class.java
            )
        initMovieList()
        retrofitViewModel.movieList.observe(this, Observer { movieList ->
            movieList?.let { it ->
                movieListAdapter.movieList.addAll(it as MutableList<Movie>)
                movieListAdapter.notifyItemRangeInserted(
                    movieListAdapter.itemCount - it.size,
                    movieListAdapter.itemCount
                )
            }
        })
        initScrollListener()
    }

    private fun initMovieList() {
        movieListAdapter = MovieListAdapter(this)
        activityMainBinding.recyclerViewMovieList.adapter = movieListAdapter
        activityMainBinding.recyclerViewMovieList.layoutManager = GridLayoutManager(this, 2)
    }

    private fun initScrollListener() {
        activityMainBinding.recyclerViewMovieList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager?
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() ==
                    recyclerView.layoutManager?.itemCount?.minus(1) ?: 0
                ) {
                    retrofitViewModel.fetchLatestMovieList()
                }
            }
        })
    }

}