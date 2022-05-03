package com.example.moviedbdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedbdemo.R
import com.example.moviedbdemo.databinding.MovieListLayoutBinding
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.retrofit.MovieAPIClient.RetrofitManager.POSTER_URL
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
                .load(POSTER_URL + it)
                .centerCrop()
                .placeholder(R.drawable.loading_image)
                .into(viewHolder.binding.imageViewPoster)
        }

        //TODO("card view click event")
        // viewholder binding with its data at the specified position
        //        viewHolder.binding.ivItemDelete.setOnClickListener(
        //            View.OnClickListener
        //            {
        //                movieList.remove(result)
        //                notifyDataSetChanged();
        //            })
    }

    override fun getItemCount() = movieList.size

    fun addResults(results: MutableList<Movie>) {
        movieList = results
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: MovieListLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}