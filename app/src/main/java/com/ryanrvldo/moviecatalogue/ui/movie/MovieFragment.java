package com.ryanrvldo.moviecatalogue.ui.movie;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ryanrvldo.moviecatalogue.adapter.MoviesAdapter;
import com.ryanrvldo.moviecatalogue.databinding.FragmentMovieBinding;
import com.ryanrvldo.moviecatalogue.viewmodel.MoviesViewModel;
import com.ryanrvldo.moviecatalogue.viewmodel.ViewModelFactory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private FragmentMovieBinding binding;

    private MoviesAdapter popularAdapter;
    private MoviesAdapter topRatedAdapter;
    private MoviesAdapter nowPlayingAdapter;

    private MoviesViewModel moviesViewModel;
    private MoviesAdapter.OnItemClicked onItemClicked = movie -> {
        MovieFragmentDirections.ActionFragmentMovieToMovieDetail action = MovieFragmentDirections.actionFragmentMovieToMovieDetail();
        action.setMovieId(movie.getId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    };

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment_movie
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        startShimmer();

        binding.rvPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new MoviesAdapter(new ArrayList<>(), onItemClicked, "movie");
        binding.rvPopular.setAdapter(popularAdapter);

        binding.rvTopRated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedAdapter = new MoviesAdapter(new ArrayList<>(), onItemClicked, "movie");
        binding.rvTopRated.setAdapter(topRatedAdapter);

        binding.rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingAdapter = new MoviesAdapter(new ArrayList<>(), onItemClicked, "movie");
        binding.rvNowPlaying.setAdapter(nowPlayingAdapter);

        moviesViewModel = obtainViewModel(requireActivity());
        observeData();
    }

    @NonNull
    private static MoviesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(MoviesViewModel.class);
    }

    private void observeData() {
        moviesViewModel.getPopularMovies().observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                popularAdapter.refillMovie(movieResponse.getMovieItems());
                hideShimmer(binding.popularShimmerContainer);
            } else {
                moviesViewModel.setPopularMovies();
                if (moviesViewModel.getPopularMovies().getValue() != null) {
                    popularAdapter.refillMovie(moviesViewModel.getPopularMovies().getValue().getMovieItems());
                }
            }
        });

        moviesViewModel.getTopRated().observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                topRatedAdapter.refillMovie(movieResponse.getMovieItems());
                hideShimmer(binding.ratedShimmerContainer);
            } else {
                moviesViewModel.setTopRatedMovies();
                if (moviesViewModel.getTopRated().getValue() != null) {
                    topRatedAdapter.refillMovie(moviesViewModel.getTopRated().getValue().getMovieItems());
                }
            }
        });

        moviesViewModel.getNowPlayingMovies().observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                nowPlayingAdapter.refillMovie(movieResponse.getMovieItems());
                hideShimmer(binding.playingShimmerContainer);
            } else {
                moviesViewModel.setNowPlayingMovies();
                if (moviesViewModel.getNowPlayingMovies().getValue() != null) {
                    nowPlayingAdapter.refillMovie(moviesViewModel.getNowPlayingMovies().getValue().getMovieItems());
                }
            }
        });

    }

    private void startShimmer() {
        binding.popularShimmerContainer.startShimmer();
        binding.ratedShimmerContainer.startShimmer();
        binding.playingShimmerContainer.startShimmer();
    }

    private void hideShimmer(ShimmerFrameLayout container) {
        container.hideShimmer();
        container.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}