package com.example.topmovies.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.topmovies.R
import com.example.topmovies.model.ScheduleAlarmManager
import com.example.topmovies.model.domain.Movie
import com.example.topmovies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.util.*

class ScrollingActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        val adapter = viewModel.getMoviePagedAdapter()
        adapter.setOnButtonClick {
            showDatePicker(it)
        }
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

    private fun showDatePicker(movie: Movie) {
        val calendar = GregorianCalendar()

        lateinit var scheduledDate: GregorianCalendar

        DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

                scheduledDate = GregorianCalendar(year, monthOfYear, dayOfMonth)

                TimePickerDialog(
                    this, TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
                        scheduledDate.set(Calendar.HOUR_OF_DAY, hours)
                        scheduledDate.set(Calendar.MINUTE, minutes)

                        ScheduleAlarmManager().setSchedule(
                            this,
                            movie,
                            Date(scheduledDate.timeInMillis)
                        )
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}