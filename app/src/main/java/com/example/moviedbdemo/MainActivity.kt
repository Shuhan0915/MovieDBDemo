package com.example.moviedbdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.moviedbdemo.databinding.ActivityMainBinding
import com.example.moviedbdemo.model.MovieModel
import com.example.moviedbdemo.retrofit.MovieAPIClient
import com.example.moviedbdemo.retrofit.MovieAPIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    //    private val API_KEY = "f4393d622fa954bb634e35c5330f9ae9"
    private lateinit var retrofitInterface: MovieAPIInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = activityMainBinding.root
        setContentView(view)
        initRetrofit()
        activityMainBinding.btnSearch.setOnClickListener { getLatestMovie() };

    }

    private fun initRetrofit() {
        retrofitInterface = MovieAPIClient.getRetrofitService()
    }

    private fun getLatestMovie() {
        lifecycleScope.launch(Dispatchers.IO) {
            val response: Response<MovieModel> =
                retrofitInterface.getLatestMovie()
            // suspend, continue when get response
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val latestMovie: MovieModel? = response.body()
                    activityMainBinding.testTextView.text = latestMovie?.originalTitle
                } else {
                    Log.i("Error ", "Response failed")
                    Log.i("Error ", response.toString())
                }
            }
        }
    }
}