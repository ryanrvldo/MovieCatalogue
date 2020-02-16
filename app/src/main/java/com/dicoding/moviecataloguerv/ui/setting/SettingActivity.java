package com.dicoding.moviecataloguerv.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.notification.AppSharedPreference;
import com.dicoding.moviecataloguerv.notification.ReminderReceiver;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingActivity";
    private static final String TOPIC = "reminder";

    private AppSharedPreference preference;
    private ReminderReceiver receiver;
    private Switch releaseSwitch;
    private Switch dailySwitch;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        preference = new AppSharedPreference(this);
        receiver = new ReminderReceiver();

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.setting));
        releaseSwitch = findViewById(R.id.switch_release);
        dailySwitch = findViewById(R.id.switch_daily);
        TextView languageSetting = findViewById(R.id.language_setting);
        constraintLayout = findViewById(R.id.constraint_layout);

        releaseSwitch.setOnClickListener(this);
        dailySwitch.setOnClickListener(this);
        languageSetting.setOnClickListener(this);

        checkReminderStatus();
    }

    private void checkReminderStatus() {
        if (preference.getStatusDailyReminder()) {
            dailySwitch.setChecked(true);
        } else {
            dailySwitch.setChecked(false);
        }

        if (preference.getStatusReleaseReminder()) {
            releaseSwitch.setChecked(true);
        } else {
            releaseSwitch.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_release:
                if (releaseSwitch.isChecked()) {
                    enableReleaseReminder();
                } else {
                    disableReleaseReminder();
                }
                break;
            case R.id.switch_daily:
                if (dailySwitch.isChecked()) {
                    enableDailyReminder();
                } else {
                    disableDailyReminder();
                }
                break;
            case R.id.language_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                finish();
                break;
        }
    }

    private String msg;

    private void enableReleaseReminder() {
        preference.saveBoolean(AppSharedPreference.RELEASE_REMINDER_STATUS, true);
        receiver.setReleaseReminder(this, "08:00", ReminderReceiver.EXTRA_MESSAGE);
        msg = getString(R.string.release_reminder_enabled);
        checkReminderStatus();
        Snackbar snackbar = Snackbar
                .make(constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    disableReleaseReminder();
                    msg = getString(R.string.release_reminder_disabled);
                    Snackbar snackbarUndo = Snackbar.make(constraintLayout, msg, Snackbar.LENGTH_SHORT);
                    snackbarUndo.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarUndo.show();
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    private void disableReleaseReminder() {
        preference.saveBoolean(AppSharedPreference.RELEASE_REMINDER_STATUS, false);
        receiver.cancelReleaseReminder(this);
        msg = getString(R.string.release_reminder_disabled);
        checkReminderStatus();
        Snackbar snackbar = Snackbar
                .make(constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    enableReleaseReminder();
                    msg = getString(R.string.release_reminder_enabled);
                    Snackbar snackbarUndo = Snackbar.make(constraintLayout, msg, Snackbar.LENGTH_SHORT);
                    snackbarUndo.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarUndo.show();
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    private void enableDailyReminder() {
        preference.saveBoolean(AppSharedPreference.DAILY_REMINDER_STATUS, true);
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String deviceToken = instanceIdResult.getToken();
            Log.d(TAG, "Refreshed token: " + deviceToken);
        });
        msg = getString(R.string.daily_reminder_enabled);
        checkReminderStatus();
        Log.d(TAG, msg);
        Snackbar snackbar = Snackbar
                .make(constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    disableDailyReminder();
                    msg = getString(R.string.daily_reminder_disabled);
                    Snackbar snackbarUndo = Snackbar.make(constraintLayout, msg, Snackbar.LENGTH_SHORT);
                    snackbarUndo.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarUndo.show();
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    private void disableDailyReminder() {
        preference.saveBoolean(AppSharedPreference.DAILY_REMINDER_STATUS, false);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC);
        msg = getString(R.string.daily_reminder_disabled);
        checkReminderStatus();
        Snackbar snackbar = Snackbar
                .make(constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    enableDailyReminder();
                    msg = getString(R.string.daily_reminder_enabled);
                    Snackbar snackbarUndo = Snackbar.make(constraintLayout, msg, Snackbar.LENGTH_SHORT);
                    snackbarUndo.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarUndo.show();
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
