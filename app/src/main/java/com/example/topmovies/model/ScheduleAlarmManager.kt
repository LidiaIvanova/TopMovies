package com.example.topmovies.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.topmovies.model.domain.Movie
import com.example.topmovies.R
import java.util.*

class ScheduleAlarmManager {
    companion object {
        const val ALARM_ACTION = "com.example.topmovies.MOVIE_ALARM"
        const val BUNDLE = "Bundle"
    }

    fun setSchedule(context: Context, movie: Movie, notificationTime: Date) {
        val intent = Intent(context, ScheduleBroadcastReceiver::class.java)
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        intent.action = ALARM_ACTION

        val bundle = Bundle()
        bundle.putParcelable(Movie.MOVIE_KEY, movie)
        intent.putExtra(BUNDLE, bundle)

        val pendingIntent = PendingIntent.getBroadcast(
            context, movie.id, intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val alarmManager = context
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val triggeringCalendar = GregorianCalendar()
        triggeringCalendar.time = notificationTime

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggeringCalendar.timeInMillis,
            pendingIntent
        )

        Toast.makeText(
            context, context.getString(R.string.notification_scheduled),
            Toast.LENGTH_LONG
        ).show()
    }
}