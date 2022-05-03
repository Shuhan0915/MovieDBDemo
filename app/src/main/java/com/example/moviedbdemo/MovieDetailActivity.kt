package com.example.moviedbdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviedbdemo.databinding.ActivityMovieDetailBinding
import com.example.moviedbdemo.retrofit.MovieAPIClient
import com.example.moviedbdemo.viewmodel.MovieDetailViewModel
/*
    MovieDetailActivity to handle the view about movie detail
 */
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var activityMovieDetailBinding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view: View = activityMovieDetailBinding.root
        setContentView(view)

        initViewModel()

        getMovieById()
    }

    private fun initViewModel() {
        movieDetailViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
                MovieDetailViewModel::class.java
            )
        // bind movie data and view when movie data ready
        movieDetailViewModel.movie.observe(this, Observer { movie ->
            movie?.apply {
                posterPath?.let {
                    Glide.with(baseContext)
                        .load(MovieAPIClient.POSTER_URL_W500 + it)
                        .centerCrop()
                        .placeholder(R.drawable.loading_image)
                        .into(activityMovieDetailBinding.imageViewPoster)
                }
                activityMovieDetailBinding.textViewTitle.text = title
                activityMovieDetailBinding.textViewOverview.text = overview
                activityMovieDetailBinding.textViewReleaseDate.text = releaseDate
            }
        })
    }
    /*
        Retrieve Movie ID from previous activity
        Fetch movie by ID
     */
    private fun getMovieById(){
        val movieId = intent.getIntExtra(getString(R.string.movie_id), 0)
        if (movieId != 0)
            movieDetailViewModel.fetchMovie(movieId)
    }
}