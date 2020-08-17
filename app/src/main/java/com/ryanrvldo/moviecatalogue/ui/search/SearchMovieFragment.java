package com.ryanrvldo.moviecatalogue.ui.search;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActivityNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ryanrvldo.moviecatalogue.adapter.MoviesAdapter;
import com.ryanrvldo.moviecatalogue.databinding.FragmentTabBinding;
import com.ryanrvldo.moviecatalogue.ui.detail.MovieDetailActivity;
import com.ryanrvldo.moviecatalogue.viewmodel.SearchViewModel;
import com.ryanrvldo.moviecatalogue.viewmodel.ViewModelFactory;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentTabBinding binding;

    private MoviesAdapter moviesAdapter;
    private SearchViewModel viewModel;
    private MoviesAdapter.OnItemClicked onItemClicked = movie -> {
        ActivityNavigator activityNavigator = new ActivityNavigator(requireContext());
        Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.getId());
        activityNavigator.navigate(activityNavigator.createDestination().setIntent(intent), null, null, null);
    };

    public SearchMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.refreshLayout.setOnRefreshListener(this);

        binding.rvMovies.setHasFixedSize(true);
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        moviesAdapter = new MoviesAdapter(new ArrayList<>(), onItemClicked, "search");
        binding.rvMovies.setAdapter(moviesAdapter);

        showLoading(true);
        viewModel = obtainViewModel(requireActivity());
        observeData();
    }

    @NonNull
    private static SearchViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(SearchViewModel.class);
    }

    private void observeData() {
        viewModel.getSearchMovies(SearchActivity.searchQuery).observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                moviesAdapter.refillMovie(movieResponse.getMovieItems());
                showLoading(false);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void onRefresh() {
        binding.refreshLayout.setRefreshing(true);
        viewModel.setSearchMovies(SearchActivity.searchQuery);
        observeData();
        binding.refreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}