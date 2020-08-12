package com.example.topmovies.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.topmovies.R
import com.example.topmovies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*


class ScrollingActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        moviesRecyclerView.adapter = viewModel.getMoviePagedAdapter()

        subscribeUI()

        setSupportActionBar(toolbar)
        toolbar_layout.title = title
    }

    private fun subscribeUI() {
        viewModel.getState().observe(this, Observer {
            when (it) {
                is MoviesViewModel.State.Success -> {
                    activityScrollingProgressBar.visibility = View.GONE
                }
                is MoviesViewModel.State.Loading -> {
                    activityScrollingProgressBar.visibility = View.VISIBLE
                }
                is MoviesViewModel.State.Error -> {
                    activityScrollingProgressBar.visibility = View.GONE
                    showMessage(it.message)
                }
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}