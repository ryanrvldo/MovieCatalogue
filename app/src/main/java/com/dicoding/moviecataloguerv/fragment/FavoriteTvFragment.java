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
import com.dicoding.moviecataloguerv.activity.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.adapter.FavoriteTvShowsAdapter;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView tvShowsRV;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private FavoriteTvShowsAdapter tvShowsAdapter;
    private FavoritesViewModel favoritesViewModel;
    private FavoriteTvShowsAdapter.OnItemClicked onItemClicked = new FavoriteTvShowsAdapter.OnItemClicked() {
        @Override
        public void onItemClick(TvShowItems tvShowItems) {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShowItems.getId());
            startActivity(intent);
        }
    };

    public FavoriteTvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvShowsRV = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progressBar);

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        tvShowsRV.setHasFixedSize(true);
        tvShowsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        setTvShowRV();
        showLoading(true);
        favoritesViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(Objects.requireNonNull(getActivity()).getApplication())).get(FavoritesViewModel.class);
        observeData();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoritesViewModel.deleteFavTv(tvShowsAdapter.getTvAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deleted from Tv Show favorite list.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(tvShowsRV);
    }

    private void observeData() {
        favoritesViewModel.getFavoriteTvShows().observe(getActivity(), new Observer<List<TvShowItems>>() {
            @Override
            public void onChanged(List<TvShowItems> items) {
                if (items != null) {
                    tvShowsAdapter.refillMovie(items);
                }
                showLoading(false);
            }
        });

        Log.d("FragmentTvFavorite", "Loaded");
    }

    private void setTvShowRV() {
        if (tvShowsAdapter == null) {
            tvShowsAdapter = new FavoriteTvShowsAdapter(new ArrayList<TvShowItems>(), onItemClicked);
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

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        favoritesViewModel.setFavoriteTvShows();
        observeData();
        refreshLayout.setRefreshing(false);
    }
}
