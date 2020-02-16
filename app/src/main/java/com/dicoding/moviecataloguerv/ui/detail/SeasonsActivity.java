package com.dicoding.moviecataloguerv.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.adapter.EpisodeAdapter;
import com.dicoding.moviecataloguerv.viewmodel.SeasonsViewModel;

import java.util.ArrayList;

public class SeasonsActivity extends AppCompatActivity {

    public static String TV_SHOW_ID = "tvShow_id";
    public static String SEASON_NUMBER = "season_number";

    private int tvId;
    private int seasonNumber;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);

        Toolbar toolbar = findViewById(R.id.season_toolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.progressBar);
        showLoading(true);

        tvId = getIntent().getIntExtra(TV_SHOW_ID, tvId);
        seasonNumber = getIntent().getIntExtra(SEASON_NUMBER, seasonNumber);

        EpisodeAdapter adapter = new EpisodeAdapter(new ArrayList<>());
        RecyclerView recyclerView = findViewById(R.id.rv_episode);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        SeasonsViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SeasonsViewModel.class);
        viewModel.getSeasons(tvId, seasonNumber).observe(this, episodes -> {
            if (episodes.getEpisodes() != null) {
                adapter.setEpisodes(episodes.getEpisodes());
                showLoading(false);
                if (getSupportActionBar() != null) {
                    toolbar.setTitle(episodes.getName());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
