package com.dicoding.moviecataloguerv.views.detail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dicoding.moviecataloguerv.adapter.EpisodeAdapter;
import com.dicoding.moviecataloguerv.databinding.ActivitySeasonsBinding;
import com.dicoding.moviecataloguerv.viewmodel.SeasonsViewModel;
import com.dicoding.moviecataloguerv.viewmodel.ViewModelFactory;

import java.util.ArrayList;

public class SeasonsActivity extends AppCompatActivity {

    public static String TV_SHOW_ID = "tvShow_id";
    public static String SEASON_NUMBER = "season_number";

    private int tvId;
    private int seasonNumber;

    private ActivitySeasonsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeasonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.seasonToolbar);
        showLoading(true);

        tvId = getIntent().getIntExtra(TV_SHOW_ID, tvId);
        seasonNumber = getIntent().getIntExtra(SEASON_NUMBER, seasonNumber);

        EpisodeAdapter adapter = new EpisodeAdapter(new ArrayList<>());
        binding.rvEpisode.setLayoutManager(new LinearLayoutManager(this));
        binding.rvEpisode.setHasFixedSize(true);
        binding.rvEpisode.setAdapter(adapter);

        SeasonsViewModel viewModel = obtainViewModel(this);
        viewModel.getSeasons(tvId, seasonNumber).observe(this, episodes -> {
            if (episodes.getEpisodes() != null) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(episodes.getName());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                adapter.setEpisodes(episodes.getEpisodes());
                showLoading(false);
            }
        });
    }

    @NonNull
    private static SeasonsViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(SeasonsViewModel.class);
    }

    private void showLoading(boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
