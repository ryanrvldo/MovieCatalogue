package com.dicoding.moviecataloguerv.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.MainActivity;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.network.Api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private final static String GROUP_KEY_MOVIES = "group_key_emails";
    private final static int REQUEST_CODE = 200;
    private static final int MAX_NOTIFICATION = 4;
    private String title;
    private String message;
    private int idNotification = 0;
    private List<MovieItems> stackNotif = new ArrayList<>();

    public ReminderReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        message = intent.getStringExtra(EXTRA_MESSAGE);
        if (message != null && message.equalsIgnoreCase("EXTRA_MESSAGE")) {
            getTodayReleaseMovie(context);
        }
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private void showNotification(Context context, String title, String message) {
        String CHANNEL_ID = "CHANNEL_1";
        String CHANNEL_NAME = "REMINDER_CHANNEL";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_black);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;
        if (idNotification < MAX_NOTIFICATION) {
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_movie_black)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_MOVIES)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(context.getString(R.string.new_movie) + ": " + stackNotif.get(idNotification-1).getTitle())
                    .addLine(context.getString(R.string.new_movie) + ": " + stackNotif.get(idNotification-2).getTitle())
                    .addLine(context.getString(R.string.new_movie) + ": " + stackNotif.get(idNotification-3).getTitle())
                    .setBigContentTitle(idNotification + " " + context.getString(R.string.new_movies))
                    .setSummaryText(context.getString(R.string.new_movie) + " notification");
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(idNotification + " " + context.getString(R.string.new_movies))
                    .setContentText(context.getString(R.string.app_name))
                    .setSmallIcon(R.drawable.ic_movie_black)
                    .setGroup(GROUP_KEY_MOVIES)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();
        if (notificationManager != null) {
            notificationManager.notify(idNotification, notification);
        }
    }

    public void setReleaseReminder(Context context, String time, String message) {
        final String timeFormat = "HH:mm";

        if (isDateInvalid(time, timeFormat)) {
            return;
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 200, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 200, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void getTodayReleaseMovie(final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = date.format(new Date());

        api.getNewReleaseMovie(BuildConfig.TMDB_API_KEY, currentDate, currentDate).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMovieItems() != null) {
                        title = context.getString(R.string.new_released_movies);
                        message = "";
                        for (int i = 0; i < response.body().getMovieItems().size(); i++) {
                            stackNotif.add(response.body().getMovieItems().get(i));
                            message = String.format("%s%s", message, stackNotif.get(idNotification).getTitle());
                            idNotification++;
                        }
                        showNotification(context, title, message);
                        stackNotif.clear();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e("ReleaseReminder", Objects.requireNonNull(t.getMessage()));
            }
        });

    }
}
