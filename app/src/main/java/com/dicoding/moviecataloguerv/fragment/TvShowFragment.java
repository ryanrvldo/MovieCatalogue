package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowsData;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.getTvShowCallback;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView tvShowsList;
    private ProgressBar progressBar;

    private TvShowsData tvShowsData;
    private TvShowsAdapter adapter;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tvShowsList = view.findViewById(R.id.rvTvShow);

        tvShowsData = TvShowsData.getInstance();
        tvShowsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        showLoading(true);
        getGenres();
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private TvShowsAdapter.OnItemClicked onItemClicked = new TvShowsAdapter.OnItemClicked() {
        @Override
        public void onItemClick(TvShowItems tvShowItems) {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShowItems.getId());
            startActivity(intent);
        }
    };

    private void getGenres() {
        tvShowsData.getGenres(getResources().getString(R.string.language), new getGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                getTvShows(genres);
                showLoading(false);
                tvShowsList.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getTvShows(final List<Genre> genres) {
        tvShowsData.getTvShows(getResources().getString(R.string.language), new getTvShowCallback() {
            @Override
            public void onSuccess(List<TvShowItems> tvShowItems) {
                adapter = new TvShowsAdapter(tvShowItems, getActivity(), genres, onItemClicked);
                tvShowsList.setAdapter(adapter);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(getContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }
}