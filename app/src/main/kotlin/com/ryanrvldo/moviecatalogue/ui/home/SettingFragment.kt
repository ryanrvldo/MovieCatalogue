package com.ryanrvldo.moviecatalogue.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.databinding.FragmentSettingBinding
import com.ryanrvldo.moviecatalogue.notification.ReminderReceiver
import com.ryanrvldo.moviecatalogue.utils.BaseFragment
import com.ryanrvldo.moviecatalogue.utils.Constants.DAILY_REMINDER_STATUS
import com.ryanrvldo.moviecatalogue.utils.Constants.RELEASE_REMINDER_STATUS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding!!

    @Inject
    lateinit var sharedPref: SharedPreferences

    private val receiver: ReminderReceiver = ReminderReceiver()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchRelease.setOnClickListener(this)
        binding.switchDaily.setOnClickListener(this)
        binding.languageSetting.setOnClickListener(this)
        checkReminderStatus()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.switch_release -> if (binding.switchRelease.isChecked) {
                enableReleaseReminder()
            } else {
                disableReleaseReminder()
            }
            R.id.switch_daily -> if (binding.switchDaily.isChecked) {
                enableDailyReminder()
            } else {
                disableDailyReminder()
            }
            R.id.language_setting -> {
                Intent(Settings.ACTION_LOCALE_SETTINGS).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun checkReminderStatus() {
        binding.apply {
            switchDaily.isChecked = sharedPref.getBoolean(DAILY_REMINDER_STATUS, false)
            switchRelease.isChecked = sharedPref.getBoolean(RELEASE_REMINDER_STATUS, false)
        }
    }

    private fun enableReleaseReminder() {
        receiver.setReleaseReminder(requireContext(), "08:00", ReminderReceiver.EXTRA_MESSAGE)
        sharedPref.edit {
            putBoolean(RELEASE_REMINDER_STATUS, true)
        }
        checkReminderStatus()
        showActionSnackbar(R.string.release_reminder_enabled, R.string.undo) {
            disableReleaseReminder()
        }
    }

    private fun disableReleaseReminder() {
        receiver.cancelReleaseReminder(requireContext())
        sharedPref.edit {
            putBoolean(RELEASE_REMINDER_STATUS, false)
        }
        checkReminderStatus()
        showActionSnackbar(R.string.release_reminder_disabled, R.string.undo) {
            enableReleaseReminder()
        }
    }

    private fun enableDailyReminder() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        FirebaseInstallations.getInstance().getToken(true).addOnSuccessListener {
            sharedPref.edit {
                putBoolean(DAILY_REMINDER_STATUS, true)
            }
            checkReminderStatus()
            showActionSnackbar(R.string.daily_reminder_enabled, R.string.undo) {
                disableDailyReminder()
            }
        }
    }

    private fun disableDailyReminder() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC)
        sharedPref.edit {
            putBoolean(DAILY_REMINDER_STATUS, false)
        }
        checkReminderStatus()
        showActionSnackbar(R.string.daily_reminder_disabled, R.string.undo) {
            enableDailyReminder()
        }
    }

    companion object {
        private const val TOPIC = "reminder"
    }

}
