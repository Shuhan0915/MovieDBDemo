package com.example.moviedbdemo

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbdemo.adapter.MovieListAdapter
import com.example.moviedbdemo.databinding.ActivityMainBinding
import com.example.moviedbdemo.model.Movie
import com.example.moviedbdemo.utils.SearchUtil
import com.example.moviedbdemo.viewmodel.RetrofitViewModel

/*
    Main activity for demo, launch activity
 */
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var retrofitViewModel: RetrofitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = activityMainBinding.root
        setContentView(view)
        initViewModel()
        initMovieRecyclerviewView()
        initScrollListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = menuItem.actionView as SearchView
        initSearchSuggestion(searchView)
        registerSearchSuggestionListener(searchView)
        val searchUtil = SearchUtil(retrofitViewModel)
        searchUtil.registerSearchView(searchView)
        return true
    }

    private fun initMovieRecyclerviewView() {
        movieListAdapter = MovieListAdapter(this)
        activityMainBinding.recyclerViewMovieList.adapter = movieListAdapter
        activityMainBinding.recyclerViewMovieList.layoutManager = GridLayoutManager(this, 2)
    }

    private fun initViewModel() {
        retrofitViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
                RetrofitViewModel::class.java
            )
        retrofitViewModel.movieList.observe(this, Observer { movieList ->
            movieList?.let { it ->
                if (it.isNotEmpty())
                    movieListAdapter.addResults(it as MutableList<Movie>)
            }
        })
    }

    // scroll listener for recycler view, when scroll down to the bottom,
    // invoke new api call to retrieve next page data
    private fun initScrollListener() {
        activityMainBinding.recyclerViewMovieList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager?
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() ==
                    recyclerView.layoutManager?.itemCount?.minus(1) ?: 0
                ) {
                    // detect if this is under search mode
                    if (retrofitViewModel.searchMode) {
                        retrofitViewModel.searchMovie()
                    } else {
                        retrofitViewModel.fetchMovieList()
                    }
                }
            }
        })
    }

    // init search view auto-completion list
    private fun initSearchSuggestion(searchView: SearchView) {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.suggestion_item_layout,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        searchView.suggestionsAdapter = cursorAdapter
        // update suggestion list when used type in characters in search query
        retrofitViewModel.suggestMovieList.observe(this, Observer { suggestMovieList ->
            suggestMovieList?.let { it ->
                if (it.isNotEmpty()) {
                    val columnNames = arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1)
                    val cursor = MatrixCursor(columnNames)
                    val temp = arrayOfNulls<String>(2)
                    var id = 0
                    for (item in it) {
                        temp[0] = Integer.toString(id++)
                        temp[1] = item
                        cursor.addRow(temp)
                    }
                    cursorAdapter.changeCursor(cursor)// update cursor/ suggestion list
                }
            }
        })
    }

    // register click listener for search view suggestion list item
    private fun registerSearchSuggestionListener(searchView: SearchView) {
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val index = cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1)
                if (index >= 1) {
                    val selection = cursor.getString(index)
                    searchView.setQuery(selection, true)
                }
                return true
            }
        })
    }
}