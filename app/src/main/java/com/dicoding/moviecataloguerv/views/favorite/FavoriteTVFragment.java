package com.dicoding.moviecataloguerv.views.favorite;


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

import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.databinding.FragmentTabBinding;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;
import com.dicoding.moviecataloguerv.viewmodel.ViewModelFactory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentTabBinding binding;

    private TvShowsAdapter tvShowsAdapter;
    private FavoritesViewModel favoritesViewModel;

    public FavoriteTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.refreshLayout.setOnRefreshListener(this);

        showLoading(true);

        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tvShowsAdapter = new TvShowsAdapter(new ArrayList<>(), onTvClicked, "favorite");
        binding.rvMovies.setAdapter(tvShowsAdapter);

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
        if (getActivity() != null) {
            favoritesViewModel.getFavoriteTvShows().observe(getViewLifecycleOwner(), tvShowItems -> {
                if (tvShowItems != null) {
                    tvShowsAdapter.refillTv(tvShowItems);
                    if (tvShowItems.size() == 0) {
                        binding.tvNull.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvNull.setVisibility(View.GONE);
                    }
                    showLoading(false);
                }
            });
        }
    }

    public void attachTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoritesViewModel.deleteFavTv(tvShowsAdapter.getTvShowAt(viewHolder.getAdapterPosition()));
                Toast.makeText(requireContext(), tvShowsAdapter.getTvShowAt(viewHolder.getAdapterPosition()).getTitle() + " deleted from tv show favorite list.", Toast.LENGTH_SHORT).show();
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

    private TvShowsAdapter.OnItemClicked onTvClicked = tvShow -> {
        FavoriteFragmentDirections.ActionFavoriteFragmentToTvShowDetailActivity action = FavoriteFragmentDirections.actionFavoriteFragmentToTvShowDetailActivity();
        action.setTvId(tvShow.getId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    };

    @Override
    public void onRefresh() {
        binding.refreshLayout.setRefreshing(true);
        favoritesViewModel.setFavoriteTvShows();
        observeData();
        binding.refreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
