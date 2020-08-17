package com.ryanrvldo.moviecatalogue.ui.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ryanrvldo.moviecatalogue.adapter.MoviesAdapter;
import com.ryanrvldo.moviecatalogue.databinding.FragmentTabBinding;
import com.ryanrvldo.moviecatalogue.viewmodel.FavoritesViewModel;
import com.ryanrvldo.moviecatalogue.viewmodel.ViewModelFactory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentTabBinding binding;

    private MoviesAdapter moviesAdapter;
    private FavoritesViewModel favoritesViewModel;
    private MoviesAdapter.OnItemClicked onMovieClicked = movie -> {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMovieDetailActivity action = FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailActivity();
        action.setMovieId(movie.getId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    };

    public FavoriteMovieFragment() {
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
        showLoading(true);

        binding.rvMovies.setHasFixedSize(true);
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        moviesAdapter = new MoviesAdapter(new ArrayList<>(), onMovieClicked, "favorite");
        binding.rvMovies.setAdapter(moviesAdapter);

        favoritesViewModel = obtainViewModel(requireActivity());
        observeData();
        attachTouchHelper();
    }

    @NonNull
    private static FavoritesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(FavoritesViewModel.class);
    }

    public void observeData() {
        favoritesViewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), movieItems -> {
            if (movieItems != null) {
                moviesAdapter.refillMovie(movieItems);
                if (movieItems.size() == 0) {
                    binding.tvNull.setVisibility(View.VISIBLE);
                } else {
                    binding.tvNull.setVisibility(View.GONE);
                }
                showLoading(false);
            }
        });
    }

    public void attachTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoritesViewModel.deleteFavMovie(moviesAdapter.getMovieAt(viewHolder.getAdapterPosition()));
                Toast.makeText(requireContext(), moviesAdapter.getMovieAt(viewHolder.getAdapterPosition()).getTitle() + " deleted from movies favorite list.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.rvMovies);
    }

    private void showLoading(Boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        binding.refreshLayout.setRefreshing(true);
        favoritesViewModel.setFavoriteMovies();
        observeData();
        binding.refreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
