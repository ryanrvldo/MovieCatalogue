package com.dicoding.moviecataloguerv.notification;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
    public static final String DAILY_REMINDER_STATUS = "DAILY_REMINDER_STATUS";
    public static final String RELEASE_REMINDER_STATUS = "RELEASE_REMINDER_STATUS";
    private static final String APP_NAME = "MOVIE_CATALOGUE";

    private final SharedPreferences preferences;

    public AppSharedPreference(Context context) {
        preferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    public void saveBoolean(String keySP, boolean status) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(keySP, status);
        editor.apply();
    }

    public Boolean getStatusDailyReminder() {
        return preferences.getBoolean(DAILY_REMINDER_STATUS, false);
    }

    public Boolean getStatusReleaseReminder() {
        return preferences.getBoolean(RELEASE_REMINDER_STATUS, false);
    }
}
