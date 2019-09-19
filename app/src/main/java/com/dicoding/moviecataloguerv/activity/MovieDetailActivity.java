package com.dicoding.moviecataloguerv.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieViewModel;
import com.dicoding.moviecataloguerv.model.MoviesData;
import com.dicoding.moviecataloguerv.model.Trailer;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.onGetMovieCallback;
import com.dicoding.moviecataloguerv.network.onGetTrailersCallback;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    public static String MOVIE_ID = "movie_id";

    private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780";
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieGenres;
    private TextView movieOverview;
    private TextView movieOverviewLabel;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private LinearLayout movieTrailers;
    private TextView trailersLabel;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;

    private MoviesData moviesData;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);
        moviesData = MoviesData.getInstance();

//        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
//        movieViewModel.init();
//        movieViewModel.getMoviesData().observe(this, MovieResponse);

        setupToolbar();
        initUI();
        showLoading(true);
        getMovie();
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
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
        movieOverviewLabel = findViewById(R.id.overviewLabel);
        movieReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieRating = findViewById(R.id.movieDetailsRating);
        movieTrailers = findViewById(R.id.movieTrailers);
        trailersLabel = findViewById(R.id.trailersLabel);
        progressBar = findViewById(R.id.progressBar);
    }

    private void getMovie() {
        moviesData.getMovie(movieId, getResources().getString(R.string.language), new onGetMovieCallback() {
            @Override
            public void onSuccess(MovieItems movieItems) {
                movieTitle.setText(movieItems.getTitle());
                movieOverviewLabel.setVisibility(View.VISIBLE);
                movieOverview.setText(movieItems.getOverview());
                movieRating.setVisibility(View.VISIBLE);
                movieRating.setRating(movieItems.getRating() / 2);
                movieReleaseDate.setText(movieItems.getReleaseDate());
                getGenres(movieItems);
                getTrailers(movieItems);
                if (!isFinishing()) {
                    Glide.with(MovieDetailActivity.this)
                            .load(IMAGE_BASE_URL + movieItems.getBackdrop())
                            .error(R.drawable.error)
                            .placeholder(R.drawable.placeholder)
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                            .into(movieBackdrop);
                    showLoading(false);
                }
            }

            @Override
            public void onError() {
                finish();
            }
        });
    }

    private void getGenres(final MovieItems movieItems) {
        moviesData.getGenres(getResources().getString(R.string.language), new getGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                if (movieItems.getGenres() != null) {
                    List<String> currentGenres = new ArrayList<>();
                    for (Genre genre : movieItems.getGenres()) {
                        currentGenres.add(genre.getName());
                    }
                    movieGenres.setText(TextUtils.join(", ", currentGenres));
                }
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getTrailers(MovieItems movieItems) {
        moviesData.getTrailers(movieItems.getId(), getResources().getString(R.string.language), new onGetTrailersCallback() {
            @Override
            public void onSuccess(List<Trailer> trailers) {
                trailersLabel.setVisibility(View.VISIBLE);
                movieTrailers.removeAllViews();
                for (final Trailer trailer : trailers) {
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
                            .load(String.format(YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                            .into(thumbnail);
                    movieTrailers.addView(parent);
                }
            }

            @Override
            public void onError() {
                trailersLabel.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showError() {
        Toast.makeText(MovieDetailActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }

    private void showTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
