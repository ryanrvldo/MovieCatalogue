package com.dicoding.moviecataloguerv.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActivityNavigator;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.databinding.FragmentSettingBinding;
import com.dicoding.moviecataloguerv.notification.AppSharedPreference;
import com.dicoding.moviecataloguerv.notification.ReminderReceiver;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private FragmentSettingBinding binding;

    private static final String TAG = "SettingActivity";
    private static final String TOPIC = "reminder";

    private AppSharedPreference preference;
    private ReminderReceiver receiver;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preference = new AppSharedPreference(requireContext());
        receiver = new ReminderReceiver();


        binding.switchRelease.setOnClickListener(this);
        binding.switchDaily.setOnClickListener(this);
        binding.languageSetting.setOnClickListener(this);

        checkReminderStatus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_release:
                if (binding.switchRelease.isChecked()) {
                    enableReleaseReminder();
                } else {
                    disableReleaseReminder();
                }
                break;
            case R.id.switch_daily:
                if (binding.switchDaily.isChecked()) {
                    enableDailyReminder();
                } else {
                    disableDailyReminder();
                }
                break;
            case R.id.language_setting:
                ActivityNavigator navigator = new ActivityNavigator(requireContext());
                navigator.navigate(navigator.createDestination().setIntent(new Intent(Settings.ACTION_LOCALE_SETTINGS)), null, null, null);
                break;
        }
    }

    private void checkReminderStatus() {
        if (preference.getStatusDailyReminder()) {
            binding.switchDaily.setChecked(true);
        } else {
            binding.switchDaily.setChecked(false);
        }

        if (preference.getStatusReleaseReminder()) {
            binding.switchRelease.setChecked(true);
        } else {
            binding.switchRelease.setChecked(false);
        }
    }

    private String msg;

    private void enableReleaseReminder() {
        preference.saveBoolean(AppSharedPreference.RELEASE_REMINDER_STATUS, true);
        receiver.setReleaseReminder(requireContext(), "08:00", ReminderReceiver.EXTRA_MESSAGE);
        msg = getString(R.string.release_reminder_enabled);
        checkReminderStatus();
        Snackbar snackbar = Snackbar
                .make(binding.constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    disableReleaseReminder();
                    msg = getString(R.string.release_reminder_disabled);
                    Snackbar snackbarUndo = Snackbar.make(binding.constraintLayout, msg, Snackbar.LENGTH_SHORT);
                    snackbarUndo.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarUndo.show();
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    private void disableReleaseReminder() {
        preference.saveBoolean(AppSharedPreference.RELEASE_REMINDER_STATUS, false);
        receiver.cancelReleaseReminder(requireContext());
        msg = getString(R.string.release_reminder_disabled);
        checkReminderStatus();
        Snackbar snackbar = Snackbar
                .make(binding.constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    enableReleaseReminder();
                    msg = getString(R.string.release_reminder_enabled);
                    Snackbar snackbarUndo = Snackbar.make(binding.constraintLayout, msg, Snackbar.LENGTH_SHORT);
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
                .make(binding.constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    disableDailyReminder();
                    msg = getString(R.string.daily_reminder_disabled);
                    Snackbar snackbarUndo = Snackbar.make(binding.constraintLayout, msg, Snackbar.LENGTH_SHORT);
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
                .make(binding.constraintLayout, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    enableDailyReminder();
                    msg = getString(R.string.daily_reminder_enabled);
                    Snackbar snackbarUndo = Snackbar.make(binding.constraintLayout, msg, Snackbar.LENGTH_SHORT);
                    snackbarUndo.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarUndo.show();
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}