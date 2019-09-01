package com.dicoding.moviecataloguerv.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.TvShow;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    ImageView tsPoster;
    TextView tsTitle, tsDesc, tsRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tsPoster = findViewById(R.id.ts_Poster);
        tsTitle = findViewById(R.id.ts_Title);
        tsDesc = findViewById(R.id.ts_Desc);
        tsRelease = findViewById(R.id.ts_Release);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        assert tvShow != null;
        tsTitle.setText(tvShow.getTitle());
        tsDesc.setText(tvShow.getDesc());
        tsRelease.setText(tvShow.getRelease());

        Glide.with(this)
                .load(tvShow.getPoster())
                .apply(new RequestOptions().override(500, 750))
                .into(tsPoster);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(tvShow.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
