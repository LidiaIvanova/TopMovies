package com.example.topmovies.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import com.example.topmovies.model.domain.Movie
import com.example.topmovies.R


class ScheduleBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val bundle = intent.getBundleExtra(ScheduleAlarmManager.BUNDLE)
        val currentMovie: Movie? = bundle?.getParcelable(Movie.MOVIE_KEY)

        currentMovie?.let {
            val builder = NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_short_text, currentMovie.title))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(context.getString(R.string.notification_long_text, currentMovie.title, currentMovie.overview))
                )
                .setVibrate(longArrayOf(500, 500, 500, 500))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_MAX)

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.channel_name)
                val descriptionText = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                channel.lockscreenVisibility = View.VISIBLE
                channel.setShowBadge(true)
                channel.vibrationPattern = longArrayOf(500, 500, 500, 500)
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(currentMovie.id, builder.build())
        }
    }

    companion object {
        const val CHANNEL_ID = "TopMoviesChannel"
    }
}