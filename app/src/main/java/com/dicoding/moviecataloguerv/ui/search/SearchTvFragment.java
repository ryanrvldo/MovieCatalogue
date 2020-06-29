package com.dicoding.moviecataloguerv.ui.search;


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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.ui.detail.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.viewmodel.SearchViewModel;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTvFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private TvShowsAdapter adapter;
    private SearchViewModel viewModel;

    public SearchTvFragment() {
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
        adapter = new TvShowsAdapter(new ArrayList<>(), onItemClicked, "search");
        recyclerView.setAdapter(adapter);

        showLoading(true);
        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
            observeData();
        }
    }

    private TvShowsAdapter.OnItemClicked onItemClicked = tvShow -> {
        Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
        intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShow.getId());
        startActivity(intent);
    };

    private void observeData() {
        viewModel.getSearchTv(SearchActivity.searchQuery).observe(getViewLifecycleOwner(), tvShowResponse -> {
            if (tvShowResponse != null) {
                adapter.refillTv(tvShowResponse.getTvShowItems());
                showLoading(false);
            }
        });

        Log.d("FragmentSearchTv", "Loaded");
    }

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
        viewModel.setSearchTv(SearchActivity.searchQuery);
        observeData();
        refreshLayout.setRefreshing(false);
    }
}