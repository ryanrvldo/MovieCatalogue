package com.dicoding.moviecataloguerv.ui.favorite;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.ui.detail.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView tvShowsFavorite;
    private TextView tvShowNull;
    private TvShowsAdapter tvShowsAdapter;

    private FavoritesViewModel favoritesViewModel;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    public FavoriteTVFragment() {
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

        tvShowsFavorite = view.findViewById(R.id.rvMovies);
        tvShowNull = view.findViewById(R.id.tv_null);
        progressBar = view.findViewById(R.id.progressBar);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        showLoading(true);

        tvShowsFavorite.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tvShowsAdapter = new TvShowsAdapter(new ArrayList<>(), onTvClicked, "favorite");
        tvShowsFavorite.setAdapter(tvShowsAdapter);
        observeData();
    }

    public void observeData() {
        if (getActivity() != null) {
            favoritesViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FavoritesViewModel.class);
            favoritesViewModel.getFavoriteTvShows().observe(getViewLifecycleOwner(), tvShowItems -> {
                if (tvShowItems != null) {
                    tvShowsAdapter.refillTv(tvShowItems);
                    if (tvShowItems.size() == 0) {
                        tvShowNull.setVisibility(View.VISIBLE);
                    } else {
                        tvShowNull.setVisibility(View.GONE);
                    }
                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            favoritesViewModel.deleteFavTv(tvShowsAdapter.getTvShowAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(getActivity(), tvShowsAdapter.getTvShowAt(viewHolder.getAdapterPosition()).getTitle() + " deleted from tv show favorite list.", Toast.LENGTH_SHORT).show();
                        }
                    }).attachToRecyclerView(tvShowsFavorite);
                    showLoading(false);
                }
            });
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private TvShowsAdapter.OnItemClicked onTvClicked = tvShow -> {
        Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
        intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShow.getId());
        startActivity(intent);
    };

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        favoritesViewModel.setFavoriteTvShows();
        observeData();
        refreshLayout.setRefreshing(false);
    }
}
