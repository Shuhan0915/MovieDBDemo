package com.example.moviedbdemo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedbdemo.MovieDetailActivity
import com.example.moviedbdemo.R
import com.example.moviedbdemo.databinding.MovieListLayoutBinding
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.retrofit.MovieAPIClient.RetrofitManager.POSTER_URL_W200

/*
    Movie List Adapter to hold the latest movie list data and bind data into recycler view
 */
class MovieListAdapter(var context: Context, var movieList: MutableList<Movie> = mutableListOf()) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        // Inflate the view from an XML layout file
        val binding: MovieListLayoutBinding =
            MovieListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // construct the viewholder with the view binding
        return ViewHolder(binding)
    }

    // binds the view holder created with data that will be displayed
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item: Movie = movieList[position]
        viewHolder.binding.textViewTitle.text = item.title
        viewHolder.binding.textViewVoteAverage.text = item.voteAverage
        item.posterPath?.let {
            Glide.with(context)
                .load(POSTER_URL_W200 + it)
                .centerCrop()
                .placeholder(R.drawable.loading_image)
                .into(viewHolder.binding.imageViewPoster)
        }
        // register card view click event
        // on click card view, navigate to the movie detail activity
        viewHolder.binding.cardView.setOnClickListener(
            View.OnClickListener
            {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(context.getString(R.string.movie_id), item.movieId)
                context.startActivity(intent)
            })
    }

    override fun getItemCount() = movieList.size

    fun addResults(results: MutableList<Movie>) {
        movieList = results
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: MovieListLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}