package com.dicoding.moviecataloguerv.activity;

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
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.Similar;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.Trailer;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;
import com.dicoding.moviecataloguerv.viewmodel.TvShowsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class TvShowDetailActivity extends AppCompatActivity {

    public static String TV_SHOW_ID = "tvShow_id";

    private TvShowsViewModel tvShowsViewModel;
    private FavoritesViewModel favoritesViewModel;

    private ImageView tvShowBackdrop;
    private TextView tvShowTitle;
    private TextView tvShowGenres;
    private TextView tvShowOverview;
    private TextView tvShowReleaseDate;
    private RatingBar tvShowRating;
    private LinearLayout tvShowTrailers;
    private LinearLayout tvShowSimilar;
    private ProgressBar progressBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView backgroundLoading;
    private AppBarLayout appBarLayout;
    private ConstraintLayout constraintLayout;
    private TextView tvShowTrailerLabel;
    private TextView tvShowSimilarLabel;
    private TextView ratingText;

    private String language;
    private int tvShowId;
    private TvShowItems tvShowItems;
    private boolean favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        language = getResources().getString(R.string.language);

        setupToolbar();
        initUI();
        showLoading(true);

        tvShowId = getIntent().getIntExtra(TV_SHOW_ID, tvShowId);

        tvShowsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        favoritesViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(FavoritesViewModel.class);
        observeData();
    }

    private void observeData() {
        tvShowsViewModel.getTvShowItems(tvShowId, language).observe(this, new Observer<TvShowItems>() {
            @Override
            public void onChanged(TvShowItems items) {
                if (items != null) {
                    setTvShow();
                    if (favoritesViewModel.selectFavTv(tvShowId) != null) {
                        favorite = true;
                        invalidateOptionsMenu();
                    }
                }
            }
        });
        tvShowsViewModel.getGenres(language).observe(this, new Observer<GenresResponse>() {
            @Override
            public void onChanged(GenresResponse genresResponse) {
                if (genresResponse != null) {
                    setGenres();
                }
            }
        });
        tvShowsViewModel.getTrailers(tvShowId).observe(this, new Observer<TrailerResponse>() {
            @Override
            public void onChanged(TrailerResponse trailerResponse) {
                if (trailerResponse != null) {
                    setTrailers();
                }
            }
        });
        tvShowsViewModel.getSimilar(tvShowId).observe(this, new Observer<SimilarResponse>() {
            @Override
            public void onChanged(SimilarResponse similarResponse) {
                if (similarResponse != null) {
                    setSimilar();
                    showLoading(false);
                }
            }
        });
        Log.d("TvShowDetail", "Loaded");
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
        tvShowBackdrop = findViewById(R.id.movieDetailsBackdrop);
        tvShowTitle = findViewById(R.id.movieDetailsTitle);
        tvShowGenres = findViewById(R.id.movieDetailsGenres);
        tvShowOverview = findViewById(R.id.movieDetailsOverview);
        tvShowReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        tvShowRating = findViewById(R.id.movieDetailsRating);
        tvShowTrailers = findViewById(R.id.movieTrailers);
        tvShowSimilar = findViewById(R.id.movieSimilar);
        progressBar = findViewById(R.id.progressBar);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        backgroundLoading = findViewById(R.id.background_loading);
        appBarLayout = findViewById(R.id.app_bar);
        constraintLayout = findViewById(R.id.constraint);
        tvShowTrailerLabel = findViewById(R.id.trailersLabel);
        tvShowSimilarLabel = findViewById(R.id.similarLabel);
        ratingText = findViewById(R.id.rating_text);
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

    private void setTvShow() {
        this.tvShowItems = tvShowsViewModel.getTvShowItems(tvShowId, language).getValue();
        if (tvShowItems == null) {
            showError();
        } else {
            tvShowTitle.setText(tvShowItems.getTitle());
            tvShowOverview.setText(tvShowItems.getOverview());
            tvShowRating.setRating(tvShowItems.getRating() / 2);
            ratingText.setText(String.format("%s / 10", String.valueOf(tvShowItems.getRating())));
            tvShowReleaseDate.setText(tvShowItems.getReleaseDate());
            collapsingToolbar.setTitle(tvShowItems.getTitle());

            if (tvShowItems.getGenres() != null) {
                ArrayList<String> currentGenres = new ArrayList<>();
                for (Genre genre : tvShowItems.getGenres()) {
                    currentGenres.add(genre.getName());
                }
                tvShowGenres.setText(TextUtils.join(", ", currentGenres));
            }

            Glide.with(TvShowDetailActivity.this)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + tvShowItems.getBackdrop())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                    .into(tvShowBackdrop);
        }
    }

    private void setGenres() {
        GenresResponse genresResponse = tvShowsViewModel.getGenres(language).getValue();
        if (genresResponse != null && genresResponse.getGenres() == null) {
            showError();
        }
    }

    private void setTrailers() {
        TrailerResponse trailerResponse = tvShowsViewModel.getTrailers(tvShowId).getValue();
        tvShowTrailers.removeAllViews();
        if (trailerResponse != null) {
            if (trailerResponse.getTrailers() == null) {
                tvShowTrailerLabel.setVisibility(View.GONE);
                tvShowTrailers.setVisibility(View.GONE);
                showError();
            } else {
                for (final Trailer trailer : trailerResponse.getTrailers()) {
                    View parent = getLayoutInflater().inflate(R.layout.thumbnail_trailer, tvShowTrailers, false);
                    ImageView thumbnail = parent.findViewById(R.id.thumbnail_trailer);
                    TextView tvTrailerTitle = parent.findViewById(R.id.trailerTitle);

                    tvTrailerTitle.setText(trailer.getName());
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
        } else {
            showError();
            tvShowTrailerLabel.setVisibility(View.GONE);
            tvShowTrailers.setVisibility(View.GONE);
        }
    }

    private void setSimilar() {
        SimilarResponse similarResponse = tvShowsViewModel.getSimilar(tvShowId).getValue();
        tvShowSimilar.removeAllViews();
        if (similarResponse != null) {
            if (similarResponse.getSimilar() == null) {
                tvShowSimilarLabel.setVisibility(View.GONE);
                tvShowSimilar.setVisibility(View.GONE);
                showError();
            } else {
                for (final Similar similar : similarResponse.getSimilar()) {
                    View parent = getLayoutInflater().inflate(R.layout.thumbnail_similar, tvShowSimilar, false);
                    ImageView thumbnailSimilar = parent.findViewById(R.id.thumbnail_similar);
                    TextView tvSimilarTitle = parent.findViewById(R.id.similarMovieTitle);
                    TextView tvSimilarRating = parent.findViewById(R.id.cv_movie_rating);

                    tvSimilarTitle.setText(similar.getName());
                    tvSimilarRating.setText(String.valueOf(similar.getRating()));

                    thumbnailSimilar.requestLayout();
                    thumbnailSimilar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(TvShowDetailActivity.this, TvShowDetailActivity.class);
                            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, similar.getId());
                            startActivity(intent);
                        }
                    });

                    Glide.with(TvShowDetailActivity.this)
                            .load(BuildConfig.TMDB_IMAGE_BASE_URL + similar.getPosterPath())
                            .error(R.drawable.ic_broken_image)
                            .placeholder(R.drawable.ic_image)
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark).centerCrop())
                            .into(thumbnailSimilar);
                    tvShowSimilar.addView(parent);
                }
            }
        } else {
            showError();
            tvShowSimilarLabel.setVisibility(View.GONE);
            tvShowSimilar.setVisibility(View.GONE);
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
        String msg;
        if (item.getItemId() == R.id.favorite) {
            if (favoritesViewModel.selectFavTv(tvShowId) == null) {
                favoritesViewModel.addFavoriteTvShow(tvShowItems);
                favorite = true;
                item.setIcon(R.drawable.ic_favorite);
                msg = getString(R.string.add_favorite_tv);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            } else {
                favoritesViewModel.deleteFavTv(tvShowItems);
                favorite = false;
                item.setIcon(R.drawable.ic_favorite_border);
                msg = getString(R.string.delete_favorite_tv);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
