package com.ryanrvldo.moviecatalogue.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.remote.TmdbAPI
import com.ryanrvldo.moviecatalogue.data.remote.response.MovieResponse
import com.ryanrvldo.moviecatalogue.ui.newRelease.NewReleaseActivity
import com.ryanrvldo.moviecatalogue.utils.Constants.CHANNEL_ID
import com.ryanrvldo.moviecatalogue.utils.Constants.CHANNEL_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReminderReceiver : BroadcastReceiver() {
    private var title: String? = null
    private var message: String? = null
    private var idNotification = 0
    private val stackNotification: MutableList<Movie> = ArrayList()
    override fun onReceive(context: Context, intent: Intent) {
        message = intent.getStringExtra(EXTRA_MESSAGE)
        if (message != null && message.equals("EXTRA_MESSAGE", ignoreCase = true)) {
            getTodayReleaseMovie(context)
        }
    }

    private fun isDateInvalid(date: String, format: String?): Boolean {
        return try {
            val dateFormat: DateFormat = SimpleDateFormat(format, Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    private fun showNotification(context: Context, title: String, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_movie_black)
        val intent = Intent(context, NewReleaseActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder: NotificationCompat.Builder
        mBuilder = if (idNotification < MAX_NOTIFICATION) {
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_movie_black)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_KEY_MOVIES)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine(context.getString(R.string.new_movie) + ": " + stackNotification[idNotification - 1].title)
                .addLine(context.getString(R.string.new_movie) + ": " + stackNotification[idNotification - 2].title)
                .addLine(context.getString(R.string.new_movie) + ": " + stackNotification[idNotification - 3].title)
                .setBigContentTitle(idNotification.toString() + " " + context.getString(R.string.new_movies))
                .setSummaryText(context.getString(R.string.new_movie) + " notification")
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(idNotification.toString() + " " + context.getString(R.string.new_movies))
                .setContentText(context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_movie_black)
                .setGroup(GROUP_KEY_MOVIES)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        notificationManager.notify(idNotification, notification)
    }

    fun setReleaseReminder(context: Context, time: String, message: String?) {
        val timeFormat = "HH:mm"
        if (isDateInvalid(time, timeFormat)) {
            return
        }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        val timeArray = time.split(":".toRegex()).toTypedArray()
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = timeArray[0].toInt()
        calendar[Calendar.MINUTE] = timeArray[1].toInt()
        calendar[Calendar.SECOND] = 0
        val pendingIntent = PendingIntent.getBroadcast(context, 200, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelReleaseReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 200, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    private fun getTodayReleaseMovie(context: Context) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val tmdbAPI = retrofit.create(TmdbAPI::class.java)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = date.format(Date())
        tmdbAPI.getNewReleaseMovies(BuildConfig.TMDB_API_KEY, currentDate, currentDate)
            .enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>,
                    response: Response<MovieResponse?>
                ) {
                    if (response.isSuccessful) {
                        title = context.getString(R.string.new_released_movies)
                        message = ""
                        response.body()?.let { responseBody ->
                            for (i in responseBody.movieItems.indices) {
                                stackNotification.add(responseBody.movieItems[i])
                                message = String.format(
                                    "%s%s",
                                    message,
                                    stackNotification[idNotification].title
                                )
                                idNotification++
                            }

                        }
                        showNotification(context, title!!, message!!)
                        stackNotification.clear()
                    }
                }

                override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                    Log.e("ReleaseReminder", t.message.toString())
                }
            })
    }

    companion object {
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        private const val GROUP_KEY_MOVIES = "group_key_emails"
        private const val REQUEST_CODE = 200
        private const val MAX_NOTIFICATION = 4
    }
}