package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowResponse;
import com.dicoding.moviecataloguerv.viewmodel.TvShowsViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private ArrayList<TvShowItems> tvShowItemsArrayList = new ArrayList<>();
    private ArrayList<Genre> genreArrayList = new ArrayList<>();

    private RecyclerView tvShowsRV;
    private ProgressBar progressBar;

    private TvShowsAdapter tvShowsAdapter;
    private Observer<TvShowResponse> getTvShows = new Observer<TvShowResponse>() {
        @Override
        public void onChanged(TvShowResponse tvShowResponse) {
            ArrayList<TvShowItems> tvShowItems = tvShowResponse.getTvShowItems();
            tvShowItemsArrayList.addAll(tvShowItems);
            tvShowsAdapter.notifyDataSetChanged();
        }
    };
    private Observer<GenresResponse> getGenres = new Observer<GenresResponse>() {
        @Override
        public void onChanged(GenresResponse genresResponse) {
            ArrayList<Genre> genreItems = genresResponse.getGenres();
            genreArrayList.addAll(genreItems);
            tvShowsAdapter.notifyDataSetChanged();
            showLoading(false);
        }
    };
    private TvShowsAdapter.OnItemClicked onItemClicked = new TvShowsAdapter.OnItemClicked() {
        @Override
        public void onItemClick(TvShowItems tvShowItems) {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShowItems.getId());
            startActivity(intent);
        }
    };

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
        TvShowsViewModel tvShowsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TvShowsViewModel.class);
        Log.d("FragmentTV", "Loaded");

        tvShowsViewModel.getTvShows(getResources().getString(R.string.language)).observe(getActivity(), getTvShows);
        tvShowsViewModel.getGenres(getResources().getString(R.string.language)).observe(getActivity(), getGenres);

        setTvShowsRV();
    }

    private void setTvShowsRV() {
        if (tvShowsAdapter == null) {
            tvShowsAdapter = new TvShowsAdapter(tvShowItemsArrayList, getActivity(), genreArrayList, onItemClicked);
            tvShowsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            tvShowsRV.setAdapter(tvShowsAdapter);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            tvShowsRV.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            tvShowsRV.setVisibility(View.VISIBLE);
        }
    }
}