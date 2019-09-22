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
import com.dicoding.moviecataloguerv.model.TvShowsRepo;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.getTvShowCallback;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView tvShowsRV;
    private ProgressBar progressBar;

    private TvShowsRepo tvShowsRepo;
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

        tvShowsRV = view.findViewById(R.id.rvTvShow);
        progressBar = view.findViewById(R.id.progressBar);

        showLoading(true);
        tvShowsRepo = TvShowsRepo.getInstance();
        tvShowsRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        setGenres();
    }

    private void setGenres() {
        tvShowsRepo.getGenres(getResources().getString(R.string.language), new getGenresCallback() {
            @Override
            public void onSuccess(ArrayList<Genre> genres) {
                setTvShows(genres);
                showLoading(false);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void setTvShows(final ArrayList<Genre> genres) {
        tvShowsRepo.getTvShows(getResources().getString(R.string.language), new getTvShowCallback() {
            @Override
            public void onSuccess(ArrayList<TvShowItems> tvShowItems) {
                adapter = new TvShowsAdapter(tvShowItems, getActivity(), genres, onItemClicked);
                tvShowsRV.setAdapter(adapter);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private TvShowsAdapter.OnItemClicked onItemClicked = new TvShowsAdapter.OnItemClicked() {
        @Override
        public void onItemClick(TvShowItems tvShowItems) {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShowItems.getId());
            startActivity(intent);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            tvShowsRV.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            tvShowsRV.setVisibility(View.VISIBLE);
        }
    }

    private void showError() {
        Toast.makeText(getContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }
}