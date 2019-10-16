package com.dicoding.moviecataloguerv.notification;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
    public static final String DAILY_REMINDER_STATUS = "DAILY_REMINDER_STATUS";
    public static final String RELEASE_REMINDER_STATUS = "RELEASE_REMINDER_STATUS";
    private static final String APP_NAME = "MOVIE_CATALOGUE";
    private final SharedPreferences preference;
    private SharedPreferences.Editor editor;

    public AppSharedPreference(Context context) {
        preference = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    public void saveBoolean(String keySP, boolean status) {
        editor.putBoolean(keySP, status);
        editor = preference.edit();
        editor.apply();
    }

    public Boolean getStatusDailyReminder() {
        return preference.getBoolean(DAILY_REMINDER_STATUS, false);
    }

    public Boolean getStatusReleaseReminder() {
        return preference.getBoolean(RELEASE_REMINDER_STATUS, false);
    }
}
