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
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.Trailer;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.viewmodel.MoviesViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import static com.dicoding.moviecataloguerv.BuildConfig.YOUTUBE_VIDEO_URL;

public class MovieDetailActivity extends AppCompatActivity {

    public static String MOVIE_ID = "movie_id";

    private ArrayList<Trailer> trailerArrayList = new ArrayList<>();
    private ArrayList<Genre> genreArrayList = new ArrayList<>();

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieGenres;
    private TextView movieOverview;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private LinearLayout movieTrailers;
    private ProgressBar progressBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView backgroundLoading;
    private AppBarLayout appBarLayout;
    private ConstraintLayout constraintLayout;

    private int movieId;
    private Observer<MovieItems> getMovieItems = new Observer<MovieItems>() {
        @Override
        public void onChanged(MovieItems movieItems) {
            movieTitle.setText(movieItems.getTitle());
            movieOverview.setText(movieItems.getOverview());
            movieRating.setRating(movieItems.getRating() / 2);
            movieReleaseDate.setText(movieItems.getReleaseDate());
            collapsingToolbar.setTitle(getResources().getString(R.string.movie_detail));

            if (movieItems.getGenres() != null) {
                ArrayList<String> currentGenres = new ArrayList<>();
                for (Genre genre : movieItems.getGenres()) {
                    currentGenres.add(genre.getName());
                }
                movieGenres.setText(TextUtils.join(", ", currentGenres));
            } else {
                movieGenres.setText((CharSequence) genreArrayList);
            }

            Glide.with(MovieDetailActivity.this)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movieItems.getBackdrop())
                    .error(R.drawable.error)
                    .placeholder(R.drawable.placeholder)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                    .into(movieBackdrop);
            showLoading(false);

            movieTrailers.removeAllViews();
            for (final Trailer trailer : trailerArrayList) {
                View parent = getLayoutInflater().inflate(R.layout.thumbnail_trailer, movieTrailers, false);
                ImageView thumbnail = parent.findViewById(R.id.thumbnail_trailer);
                thumbnail.requestLayout();
                thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTrailer(String.format(YOUTUBE_VIDEO_URL, trailer.getKey()));
                    }
                });

                Glide.with(MovieDetailActivity.this)
                        .load(String.format(BuildConfig.YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                        .into(thumbnail);
                movieTrailers.addView(parent);
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
        setContentView(R.layout.activity_movie_detail);

        setupToolbar();
        initUI();
        showLoading(true);

        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);

        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        Log.d("MovieDetail", "Loaded");

        moviesViewModel.getTrailers(movieId).observe(this, getTrailers);
        moviesViewModel.getGenres(getResources().getString(R.string.language)).observe(this, getGenres);
        moviesViewModel.getMovieItems(movieId, getResources().getString(R.string.language)).observe(this, getMovieItems);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_movie);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initUI() {
        movieBackdrop = findViewById(R.id.movieDetailsBackdrop);
        movieTitle = findViewById(R.id.movieDetailsTitle);
        movieGenres = findViewById(R.id.movieDetailsGenres);
        movieOverview = findViewById(R.id.movieDetailsOverview);
        movieReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieRating = findViewById(R.id.movieDetailsRating);
        movieTrailers = findViewById(R.id.movieTrailers);
        progressBar = findViewById(R.id.progressBar);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        backgroundLoading = findViewById(R.id.background_loading);
        appBarLayout = findViewById(R.id.app_bar);
        constraintLayout = findViewById(R.id.constraint);
    }

    private void showLoading(Boolean state) {
        if (state) {
            backgroundLoading.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            appBarLayout.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.GONE);
        } else {
            backgroundLoading.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
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
