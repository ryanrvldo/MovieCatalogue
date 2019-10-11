package com.dicoding.moviecataloguerv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dicoding.moviecataloguerv.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.setting));
        Switch releaseSwitch = findViewById(R.id.switch_release);
        Switch dailySwitch = findViewById(R.id.switch_daily);
        TextView languageSetting = findViewById(R.id.language_setting);

        releaseSwitch.setOnClickListener(this);
        dailySwitch.setOnClickListener(this);
        languageSetting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_release:
                Toast.makeText(this, "Switch Release", Toast.LENGTH_SHORT).show();
                break;
            case R.id.switch_daily:
                Toast.makeText(this, "Switch Daily", Toast.LENGTH_SHORT).show();
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
