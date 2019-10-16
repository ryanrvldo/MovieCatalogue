package com.dicoding.moviecataloguerv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.notification.AppSharedPreference;
import com.dicoding.moviecataloguerv.notification.ReminderReceiver;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingActivity";

    private AppSharedPreference preference;
    private ReminderReceiver receiver;
    private Switch releaseSwitch;
    private Switch dailySwitch;

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
        String msg;
        switch (v.getId()) {
            case R.id.switch_release:
                if (releaseSwitch.isChecked()) {
                    preference.saveBoolean(AppSharedPreference.RELEASE_REMINDER_STATUS, true);
                    receiver.setReleaseReminder(this, "08:00", ReminderReceiver.EXTRA_MESSAGE);
                    msg = getString(R.string.release_reminder_enabled);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    preference.saveBoolean(AppSharedPreference.RELEASE_REMINDER_STATUS, false);
                    receiver.cancelReleaseReminder(this);
                    msg = getString(R.string.release_reminder_disabled);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch_daily:
                if (releaseSwitch.isChecked()) {
                    preference.saveBoolean(AppSharedPreference.DAILY_REMINDER_STATUS, true);
                    FirebaseMessaging.getInstance().subscribeToTopic("reminder");
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String deviceToken = instanceIdResult.getToken();
                            Log.d(TAG, "Refreshed token: " + deviceToken);
                        }
                    });
                    msg = getString(R.string.daily_reminder_enabled);
                    Log.d(TAG, msg);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    preference.saveBoolean(AppSharedPreference.DAILY_REMINDER_STATUS, false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("reminder");
                    msg = getString(R.string.daily_reminder_disabled);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.language_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                finish();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
