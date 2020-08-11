package com.example.topmovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.topmovies.R
import com.example.topmovies.viewmodel.MoviesViewModel

import androidx.lifecycle.ViewModelProvider
import com.example.topmovies.viewmodel.MoviesAdapter
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

import com.example.topmovies.model.Result
import kotlinx.android.synthetic.main.empty_list_placeholder.*

class ScrollingActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        val adapter = MoviesAdapter {
            viewModel.onMovieScheduleButtonClick()
        }
        moviesRecyclerView.adapter = adapter

        subscribeUI(adapter)

        setSupportActionBar(toolbar)
        toolbar_layout.title = title
    }

    private fun subscribeUI(adapter: MoviesAdapter) {
        viewModel.getMovies().observe(this, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    activityScrollingProgressBar.visibility = View.GONE
                    result.data?.let { adapter.submitList(it) }
                }
                Result.Status.LOADING -> activityScrollingProgressBar.visibility = View.VISIBLE
                Result.Status.ERROR -> {
                    activityScrollingProgressBar.visibility = View.GONE
                    emptyListPlaceholder.visibility = View.VISIBLE
                    result.message?.let { showMessage(it) }
                }
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}