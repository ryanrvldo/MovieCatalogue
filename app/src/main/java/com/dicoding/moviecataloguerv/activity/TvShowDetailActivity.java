package com.dicoding.moviecataloguerv.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.Trailer;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.viewmodel.TvShowsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class TvShowDetailActivity extends AppCompatActivity {

    public static String TV_SHOW_ID = "tvShow_id";

    private ArrayList<Trailer> trailerArrayList = new ArrayList<>();
    private ArrayList<Genre> genreArrayList = new ArrayList<>();

    private ImageView tvShowBackdrop;
    private TextView tvShowTitle;
    private TextView tvShowGenres;
    private TextView tvShowOverview;
    private TextView tvShowReleaseDate;
    private RatingBar tvShowRating;
    private LinearLayout tvShowTrailers;
    private ProgressBar progressBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView backgroundLoading;
    private AppBarLayout appBarLayout;
    private ConstraintLayout constraintLayout;

    private int tvShowId;
    private Observer<TvShowItems> getTvShowItems = new Observer<TvShowItems>() {
        @Override
        public void onChanged(TvShowItems tvShowItems) {
            tvShowTitle.setText(tvShowItems.getTitle());
            tvShowOverview.setText(tvShowItems.getOverview());
            tvShowRating.setRating(tvShowItems.getRating() / 2);
            tvShowReleaseDate.setText(tvShowItems.getReleaseDate());
            collapsingToolbar.setTitle(getResources().getString(R.string.tvShow_detail));

            if (tvShowItems.getGenres() != null) {
                ArrayList<String> currentGenres = new ArrayList<>();
                for (Genre genre : tvShowItems.getGenres()) {
                    currentGenres.add(genre.getName());
                }
                tvShowGenres.setText(TextUtils.join(", ", currentGenres));
            } else {
                tvShowGenres.setText((CharSequence) genreArrayList);
            }

            Glide.with(TvShowDetailActivity.this)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + tvShowItems.getBackdrop())
                    .error(R.drawable.error)
                    .placeholder(R.drawable.placeholder)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                    .into(tvShowBackdrop);
            showLoading(false);

            tvShowTrailers.removeAllViews();
            for (final Trailer trailer : trailerArrayList) {
                View parent = getLayoutInflater().inflate(R.layout.thumbnail_trailer, tvShowTrailers, false);
                ImageView thumbnail = parent.findViewById(R.id.thumbnail_trailer);
                thumbnail.requestLayout();
                thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTrailer(String.format(BuildConfig.YOUTUBE_VIDEO_URL, trailer.getKey()));
                    }
                });

                Glide.with(TvShowDetailActivity.this)
                        .load(String.format(BuildConfig.YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                        .into(thumbnail);
                tvShowTrailers.addView(parent);
            }
        }
    };
    private Observer<GenresResponse> getGenres = new Observer<GenresResponse>() {
        @Override
        public void onChanged(GenresResponse genresResponse) {
            ArrayList<Genre> genreItems = genresResponse.getGenres();
            genreArrayList.addAll(genreItems);
        }
    };
    private Observer<TrailerResponse> getTrailers = new Observer<TrailerResponse>() {
        @Override
        public void onChanged(TrailerResponse trailerResponse) {
            ArrayList<Trailer> trailerItems = trailerResponse.getTrailers();
            trailerArrayList.addAll(trailerItems);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        setupToolbar();
        initUI();
        showLoading(true);

        tvShowId = getIntent().getIntExtra(TV_SHOW_ID, tvShowId);

        TvShowsViewModel tvShowsViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
        Log.d("TvShowDetail", "Loaded");

        tvShowsViewModel.getTrailers(tvShowId).observe(this, getTrailers);
        tvShowsViewModel.getGenres(getResources().getString(R.string.language)).observe(this, getGenres);
        tvShowsViewModel.getTvShowItems(tvShowId, getResources().getString(R.string.language)).observe(this, getTvShowItems);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_tvShow);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initUI() {
        tvShowBackdrop = findViewById(R.id.tvShowDetailsBackdrop);
        tvShowTitle = findViewById(R.id.tvShowDetailsTitle);
        tvShowGenres = findViewById(R.id.tvShowDetailsGenres);
        tvShowOverview = findViewById(R.id.tvShowDetailsOverview);
        tvShowReleaseDate = findViewById(R.id.tvShowDetailsReleaseDate);
        tvShowRating = findViewById(R.id.tvShowDetailsRating);
        tvShowTrailers = findViewById(R.id.tvShowTrailers);
        progressBar = findViewById(R.id.progressBar);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        backgroundLoading = findViewById(R.id.background_loading);
        appBarLayout = findViewById(R.id.app_bar);
        constraintLayout = findViewById(R.id.constraint);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            backgroundLoading.setVisibility(View.VISIBLE);
            appBarLayout.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            backgroundLoading.setVisibility(View.GONE);
            appBarLayout.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.VISIBLE);
        }
    }

    private void showTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
