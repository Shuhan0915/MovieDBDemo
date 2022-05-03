package com.example.moviedbdemo.utils

import androidx.appcompat.widget.SearchView
import com.example.moviedbdemo.viewmodel.RetrofitViewModel

/*
    Search Unit class to handle setOnQueryTextListener and onQueryTextChange for searchView
 */
class SearchUtil(var retrofitViewModel: RetrofitViewModel) {
    fun registerSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isEmpty()) {
                    searchView.setQuery("", false)
                    searchView.clearFocus()
                    searchView.isIconified = true
                    retrofitViewModel.resetViewModel(query)
                    retrofitViewModel.searchMovie()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty() && newText.length > 1) {
                    retrofitViewModel.fetchSuggestMovieList(newText)
                }
                return true
            }
        })
    }
}