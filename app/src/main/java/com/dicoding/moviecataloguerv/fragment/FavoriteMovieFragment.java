package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.MovieDetailActivity;
import com.dicoding.moviecataloguerv.adapter.FavoriteMoviesAdapter;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView moviesRV;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private FavoriteMoviesAdapter moviesAdapter;
    private FavoritesViewModel favoritesViewModel;
    private FavoriteMoviesAdapter.OnItemClicked onItemClicked = new FavoriteMoviesAdapter.OnItemClicked() {
        @Override
        public void onItemClick(MovieItems movieItems) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_ID, movieItems.getId());
            startActivity(intent);
        }
    };


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        moviesRV = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progressBar);

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        moviesRV.setHasFixedSize(true);
        moviesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        setMoviesRV();
        showLoading(true);
        favoritesViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(Objects.requireNonNull(getActivity()).getApplication())).get(FavoritesViewModel.class);
        observeData();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoritesViewModel.deleteFavMovie(moviesAdapter.getMovieAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deleted from Movies favorite list.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(moviesRV);
    }

    private void observeData() {
        favoritesViewModel.getFavoriteMovies().observe(getActivity(), new Observer<List<MovieItems>>() {
            @Override
            public void onChanged(List<MovieItems> movieItems) {
                if (movieItems != null) {
                    moviesAdapter.refillMovie(movieItems);
                }
                showLoading(false);
            }
        });

        Log.d("FragmentMovieFavorite", "Loaded");
    }

    private void setMoviesRV() {
        if (moviesAdapter == null) {
            moviesAdapter = new FavoriteMoviesAdapter(new ArrayList<MovieItems>(), onItemClicked);
            moviesRV.setAdapter(moviesAdapter);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            moviesRV.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            moviesRV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        favoritesViewModel.setFavoriteMovies();
        observeData();
        refreshLayout.setRefreshing(false);
    }
}
