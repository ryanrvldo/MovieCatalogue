package com.ryanrvldo.moviecatalogue.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ActivityNavigator;

import com.ryanrvldo.moviecatalogue.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(() -> {
            ActivityNavigator navigator = new ActivityNavigator(this);
            navigator.navigate(navigator.createDestination().setIntent(new Intent(this, MainActivity.class)), null, null, null);
            finish();
        }, 2000);
    }
}