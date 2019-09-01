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
import com.dicoding.moviecataloguerv.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    ImageView mvPoster;
    TextView mvTitle, mvDesc, mvRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mvPoster = findViewById(R.id.mv_Poster);
        mvTitle = findViewById(R.id.mv_Title);
        mvDesc = findViewById(R.id.mv_Desc);
        mvRelease = findViewById(R.id.mv_Release);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        assert movie != null;
        mvTitle.setText(movie.getTitle());
        mvDesc.setText(movie.getDesc());
        mvRelease.setText(movie.getRelease());

        Glide.with(this)
                .load(movie.getPoster())
                .apply(new RequestOptions().override(500, 750))
                .into(mvPoster);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.getTitle());
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
