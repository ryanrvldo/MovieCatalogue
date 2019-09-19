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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.Trailer;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowsData;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.onGetTrailersCallback;
import com.dicoding.moviecataloguerv.network.onGetTvShowCallback;

import java.util.ArrayList;
import java.util.List;

public class TvShowDetailActivity extends AppCompatActivity {

    public static String TV_SHOW_ID = "tvShow_id";

    private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780";
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";

    private ImageView tvShowBackdrop;
    private TextView tvShowTitle;
    private TextView tvShowGenres;
    private TextView tvShowOverview;
    private TextView tvShowOverviewLabel;
    private TextView tvShowReleaseDate;
    private RatingBar tvShowRating;
    private LinearLayout tvShowTrailers;
    private TextView trailersLabel;
    private ProgressBar progressBar;

    private TvShowsData tvShowsData;
    private int tvShowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        tvShowId = getIntent().getIntExtra(TV_SHOW_ID, tvShowId);
        tvShowsData = TvShowsData.getInstance();

        setupToolbar();
        initUI();
        showLoading(true);
        getTvShow();
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
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
        tvShowOverviewLabel = findViewById(R.id.overviewLabel);
        tvShowReleaseDate = findViewById(R.id.tvShowDetailsReleaseDate);
        tvShowRating = findViewById(R.id.tvShowDetailsRating);
        tvShowTrailers = findViewById(R.id.tvShowTrailers);
        trailersLabel = findViewById(R.id.trailersLabel);
        progressBar = findViewById(R.id.progressBar);
    }

    private void getTvShow() {
        tvShowsData.getTvShow(tvShowId, getResources().getString(R.string.language), new onGetTvShowCallback() {
            @Override
            public void onSuccess(TvShowItems tvShowItems) {
                tvShowTitle.setText(tvShowItems.getTitle());
                tvShowOverviewLabel.setVisibility(View.VISIBLE);
                tvShowOverview.setText(tvShowItems.getOverview());
                tvShowRating.setVisibility(View.VISIBLE);
                tvShowRating.setRating(tvShowItems.getRating() / 2);
                getGenres(tvShowItems);
                tvShowReleaseDate.setText(tvShowItems.getReleaseDate());
                if (!isFinishing()) {
                    Glide.with(TvShowDetailActivity.this)
                            .load(IMAGE_BASE_URL + tvShowItems.getBackdrop())
                            .error(R.drawable.error)
                            .placeholder(R.drawable.placeholder)
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                            .into(tvShowBackdrop);
                    showLoading(false);
                }

                getTrailers(tvShowItems);
            }

            @Override
            public void onError() {
                finish();
            }
        });
    }

    private void getGenres(final TvShowItems tvShowItems) {
        tvShowsData.getGenres(getResources().getString(R.string.language), new getGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                if (tvShowItems.getGenres() != null) {
                    List<String> currentGenres = new ArrayList<>();
                    for (Genre genre : tvShowItems.getGenres()) {
                        currentGenres.add(genre.getName());
                    }
                    tvShowGenres.setText(TextUtils.join(", ", currentGenres));
                }
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getTrailers(TvShowItems tvShowItems) {
        tvShowsData.getTrailers(tvShowItems.getId(), getResources().getString(R.string.language), new onGetTrailersCallback() {
            @Override
            public void onSuccess(List<Trailer> trailers) {
                trailersLabel.setVisibility(View.VISIBLE);
                tvShowTrailers.removeAllViews();
                for (final Trailer trailer : trailers) {
                    View parent = getLayoutInflater().inflate(R.layout.thumbnail_trailer, tvShowTrailers, false);
                    ImageView thumbnail = parent.findViewById(R.id.thumbnail_trailer);
                    thumbnail.requestLayout();
                    thumbnail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showTrailer(String.format(YOUTUBE_VIDEO_URL, trailer.getKey()));
                        }
                    });
                    Glide.with(TvShowDetailActivity.this)
                            .load(String.format(YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                            .into(thumbnail);
                    tvShowTrailers.addView(parent);
                }
            }

            @Override
            public void onError() {
                trailersLabel.setVisibility(View.GONE);
            }
        });
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

    private void showError() {
        Toast.makeText(TvShowDetailActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }

}
