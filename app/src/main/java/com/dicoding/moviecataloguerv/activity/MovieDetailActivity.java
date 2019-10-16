package com.dicoding.moviecataloguerv.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.contentprovider.MovieFavorite;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.Similar;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.Trailer;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;
import com.dicoding.moviecataloguerv.viewmodel.MoviesViewModel;
import com.dicoding.moviecataloguerv.widget.FavoriteMovieWidget;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import static com.dicoding.moviecataloguerv.BuildConfig.YOUTUBE_VIDEO_URL;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.BACKDROP;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.CONTENT_URI;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.POSTER_PATH;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.RELEASE_DATE;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.TITLE;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.VOTE_AVERAGE;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns._ID;

public class MovieDetailActivity extends AppCompatActivity {

    public static String MOVIE_ID = "movie_id";

    private MoviesViewModel moviesViewModel;
    private FavoritesViewModel favoritesViewModel;

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieGenres;
    private TextView movieOverview;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private LinearLayout movieTrailers;
    private LinearLayout movieSimilar;
    private ProgressBar progressBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView backgroundLoading;
    private AppBarLayout appBarLayout;
    private ConstraintLayout constraintLayout;
    private TextView movieTrailerLabel;
    private TextView movieSimilarLabel;
    private TextView ratingText;

    private String language;
    private int movieId;
    private MovieItems movieItems;
    private boolean favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        language = getResources().getString(R.string.language);

        setupToolbar();
        initUI();
        showLoading(true);

        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);

        moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        favoritesViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(FavoritesViewModel.class);
        observeData();
    }

    private void observeData() {
        moviesViewModel.getMovieItems(movieId, language).observe(this, new Observer<MovieItems>() {
            @Override
            public void onChanged(MovieItems items) {
                if (items != null) {
                    setMovie();
                    if (favoritesViewModel.selectFavMovie(movieId) != null) {
                        favorite = true;
                        invalidateOptionsMenu();
                    }
                }
            }
        });

        moviesViewModel.getGenres(language).observe(this, new Observer<GenresResponse>() {
            @Override
            public void onChanged(GenresResponse genresResponse) {
                if (genresResponse != null) {
                    setGenres();
                }
            }
        });
        moviesViewModel.getTrailers(movieId).observe(this, new Observer<TrailerResponse>() {
            @Override
            public void onChanged(TrailerResponse trailerResponse) {
                if (trailerResponse != null) {
                    setTrailers();
                }
            }
        });
        moviesViewModel.getSimilar(movieId).observe(this, new Observer<SimilarResponse>() {
            @Override
            public void onChanged(SimilarResponse similarResponse) {
                if (similarResponse != null) {
                    setSimilar();
                    showLoading(false);
                }
            }
        });

        Log.d("MovieDetail", "Loaded");
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
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
        movieSimilar = findViewById(R.id.movieSimilar);
        progressBar = findViewById(R.id.progressBar);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        backgroundLoading = findViewById(R.id.background_loading);
        appBarLayout = findViewById(R.id.app_bar);
        constraintLayout = findViewById(R.id.constraint);
        movieTrailerLabel = findViewById(R.id.trailersLabel);
        movieSimilarLabel = findViewById(R.id.similarLabel);
        ratingText = findViewById(R.id.rating_text);
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

    private void setMovie() {
        this.movieItems = moviesViewModel.getMovieItems(movieId, language).getValue();
        if (movieItems == null) {
            showError();
        } else {
            movieTitle.setText(movieItems.getTitle());
            movieOverview.setText(movieItems.getOverview());
            movieRating.setRating(movieItems.getRating() / 2);
            ratingText.setText(String.format("%s / 10", String.valueOf(movieItems.getRating())));
            movieReleaseDate.setText(movieItems.getReleaseDate());
            collapsingToolbar.setTitle(movieItems.getTitle());

            if (movieItems.getGenres() != null) {
                ArrayList<String> currentGenres = new ArrayList<>();
                for (Genre genre : movieItems.getGenres()) {
                    currentGenres.add(genre.getName());
                }
                movieGenres.setText(TextUtils.join(", ", currentGenres));
            }

            Glide.with(MovieDetailActivity.this)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movieItems.getBackdrop())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                    .into(movieBackdrop);
        }
    }

    private void setGenres() {
        GenresResponse genresResponse = moviesViewModel.getGenres(language).getValue();
        if (genresResponse != null && genresResponse.getGenres() == null) {
            showError();
        }
    }

    private void setTrailers() {
        TrailerResponse trailerResponse = moviesViewModel.getTrailers(movieId).getValue();
        movieTrailers.removeAllViews();
        if (trailerResponse != null) {
            if (trailerResponse.getTrailers() == null) {
                movieTrailerLabel.setVisibility(View.GONE);
                movieTrailers.setVisibility(View.GONE);
                showError();
            } else {
                for (final Trailer trailer : trailerResponse.getTrailers()) {
                    View parent = getLayoutInflater().inflate(R.layout.thumbnail_trailer, movieTrailers, false);
                    ImageView thumbnailTrailer = parent.findViewById(R.id.thumbnail_trailer);
                    TextView movieTrailerTitle = parent.findViewById(R.id.trailerTitle);

                    movieTrailerTitle.setText(trailer.getName());

                    Glide.with(MovieDetailActivity.this)
                            .load(String.format(BuildConfig.YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                            .into(thumbnailTrailer);

                    thumbnailTrailer.requestLayout();
                    thumbnailTrailer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showTrailer(String.format(YOUTUBE_VIDEO_URL, trailer.getKey()));
                        }
                    });
                    movieTrailers.addView(parent);
                }
            }
        } else {
            showError();
            movieTrailerLabel.setVisibility(View.GONE);
            movieTrailers.setVisibility(View.GONE);
        }
    }

    private void setSimilar() {
        SimilarResponse similarResponse = moviesViewModel.getSimilar(movieId).getValue();
        movieSimilar.removeAllViews();
        if (similarResponse != null) {
            if (similarResponse.getSimilar() == null) {
                movieSimilarLabel.setVisibility(View.GONE);
                movieSimilar.setVisibility(View.GONE);
                showError();
            } else {
                for (final Similar similar : similarResponse.getSimilar()) {
                    View parent = getLayoutInflater().inflate(R.layout.thumbnail_similar, movieSimilar, false);
                    ImageView thumbnailSimilar = parent.findViewById(R.id.thumbnail_similar);
                    TextView movieSimilarTitle = parent.findViewById(R.id.similarMovieTitle);
                    TextView movieSimilarRating = parent.findViewById(R.id.cv_movie_rating);

                    movieSimilarTitle.setText(similar.getTitle());
                    movieSimilarRating.setText(String.valueOf(similar.getRating()));


                    Glide.with(MovieDetailActivity.this)
                            .load(BuildConfig.TMDB_IMAGE_BASE_URL + similar.getPosterPath())
                            .error(R.drawable.ic_broken_image)
                            .placeholder(R.drawable.ic_image)
                            .into(thumbnailSimilar);

                    thumbnailSimilar.requestLayout();
                    thumbnailSimilar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MovieDetailActivity.this, MovieDetailActivity.class);
                            intent.putExtra(MovieDetailActivity.MOVIE_ID, similar.getId());
                            startActivity(intent);
                        }
                    });
                    movieSimilar.addView(parent);
                }
            }
        } else {
            showError();
            movieSimilarLabel.setVisibility(View.GONE);
            movieSimilar.setVisibility(View.GONE);
        }
    }

    private void showError() {
        Toast.makeText(this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (favorite) {
            menu.findItem(R.id.favorite).setIcon(R.drawable.ic_favorite);
        } else {
            menu.findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_border);
        }
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MovieFavorite movieFavorite = new MovieFavorite();
        movieFavorite.setId(movieItems.getId());
        movieFavorite.setTitle(movieItems.getTitle());
        movieFavorite.setVoteAverage(movieItems.getRating());
        movieFavorite.setReleaseDate(movieItems.getReleaseDate());
        movieFavorite.setPosterPath(movieItems.getPosterPath());
        movieFavorite.setBackdrop(movieItems.getBackdrop());

        ContentValues values = new ContentValues();
        values.put(_ID, movieItems.getId());
        values.put(TITLE, movieItems.getTitle());
        values.put(VOTE_AVERAGE, movieItems.getRating());
        values.put(RELEASE_DATE, movieItems.getReleaseDate());
        values.put(POSTER_PATH, movieItems.getPosterPath());
        values.put(BACKDROP, movieItems.getBackdrop());

        String msg;
        if (item.getItemId() == R.id.favorite) {
            if (favoritesViewModel.selectFavMovie(movieId) == null) {
                favoritesViewModel.addFavoriteMovie(movieItems);
                FavoriteMovieWidget.updateWidget(this);

                getContentResolver().insert(CONTENT_URI, values);

                favorite = true;
                item.setIcon(R.drawable.ic_favorite);
                msg = getString(R.string.add_favorite_movie);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            } else {
                favoritesViewModel.deleteFavMovie(movieItems);
                FavoriteMovieWidget.updateWidget(this);

                Uri uri = Uri.parse(CONTENT_URI + "/" + movieItems.getId());
                getContentResolver().delete(uri, null, null);

                favorite = false;
                item.setIcon(R.drawable.ic_favorite_border);
                msg = getString(R.string.delete_favorite_movie);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
