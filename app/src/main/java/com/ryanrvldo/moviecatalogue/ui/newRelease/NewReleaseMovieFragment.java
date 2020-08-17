package com.ryanrvldo.moviecatalogue.ui.newRelease;


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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ryanrvldo.moviecatalogue.R;
import com.ryanrvldo.moviecatalogue.adapter.MoviesAdapter;
import com.ryanrvldo.moviecatalogue.ui.detail.MovieDetailActivity;
import com.ryanrvldo.moviecatalogue.viewmodel.MoviesViewModel;
import com.ryanrvldo.moviecatalogue.viewmodel.ViewModelFactory;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewReleaseMovieFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{

    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private MoviesAdapter moviesAdapter;
    private MoviesViewModel moviesViewModel;

    public NewReleaseMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progressBar);

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        moviesAdapter = new MoviesAdapter(new ArrayList<>(), onItemClicked, "search");
        recyclerView.setAdapter(moviesAdapter);

        showLoading(true);
        if (getActivity() != null) {
            moviesViewModel = obtainViewModel(requireActivity());
            observeData();
        }
    }

    @NonNull
    private static MoviesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(MoviesViewModel.class);
    }

    private void observeData() {
        moviesViewModel.getNewReleaseMovies().observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                moviesAdapter.refillMovie(movieResponse.getMovieItems());
                showLoading(false);
            }
        });

        Log.d("NewReleaseMovies", "Loaded");
    }

    private MoviesAdapter.OnItemClicked onItemClicked = movie -> {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.getId());
        startActivity(intent);
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        moviesViewModel.setNewReleaseMovies();
        observeData();
        refreshLayout.setRefreshing(false);
    }
}