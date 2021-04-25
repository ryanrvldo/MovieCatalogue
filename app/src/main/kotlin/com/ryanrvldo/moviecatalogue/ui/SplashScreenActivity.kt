package com.ryanrvldo.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import com.ryanrvldo.moviecatalogue.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchMainActivity()
    }

    private fun launchMainActivity() = lifecycleScope.launchWhenCreated {
        delay(2000)
        val navigator = ActivityNavigator(this@SplashScreenActivity)
        navigator.navigate(
            navigator.createDestination()
                .setIntent(Intent(this@SplashScreenActivity, MainActivity::class.java)),
            null,
            null,
            null
        )
        finishAfterTransition()
    }
}