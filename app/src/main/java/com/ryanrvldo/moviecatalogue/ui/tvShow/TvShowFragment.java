package com.ryanrvldo.moviecatalogue.ui.tvShow;


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
import com.ryanrvldo.moviecatalogue.adapter.TvShowsAdapter;
import com.ryanrvldo.moviecatalogue.databinding.FragmentTvShowBinding;
import com.ryanrvldo.moviecatalogue.viewmodel.TvShowsViewModel;
import com.ryanrvldo.moviecatalogue.viewmodel.ViewModelFactory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private FragmentTvShowBinding binding;

    private TvShowsAdapter popularAdapter;
    private TvShowsAdapter topRatedAdapter;
    private TvShowsAdapter onTheAirAdapter;

    private TvShowsViewModel tvShowsViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTvShowBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        showShimmer();

        binding.rvPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new TvShowsAdapter(new ArrayList<>(), onItemClicked, "tvShow");
        binding.rvPopular.setAdapter(popularAdapter);

        binding.rvTopRated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedAdapter = new TvShowsAdapter(new ArrayList<>(), onItemClicked, "tvShow");
        binding.rvTopRated.setAdapter(topRatedAdapter);

        binding.rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        onTheAirAdapter = new TvShowsAdapter(new ArrayList<>(), onItemClicked, "tvShow");
        binding.rvNowPlaying.setAdapter(onTheAirAdapter);

        tvShowsViewModel = obtainViewModel(requireActivity());
        observeData();
    }

    @NonNull
    private static TvShowsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(TvShowsViewModel.class);
    }

    private void observeData() {
        tvShowsViewModel.getPopularTv().observe(getViewLifecycleOwner(), tvShowResponse -> {
            if (tvShowResponse != null) {
                popularAdapter.refillTv(tvShowResponse.getTvShowItems());
                hideShimmer(binding.popularShimmerContainer);
            } else {
                tvShowsViewModel.setPopularTv();
                if (tvShowsViewModel.getPopularTv().getValue() != null) {
                    popularAdapter.refillTv(tvShowsViewModel.getPopularTv().getValue().getTvShowItems());
                }
            }
        });

        tvShowsViewModel.getTopRatedTv().observe(getViewLifecycleOwner(), tvShowResponse -> {
            if (tvShowResponse != null) {
                topRatedAdapter.refillTv(tvShowResponse.getTvShowItems());
                hideShimmer(binding.ratedShimmerContainer);
            } else {
                tvShowsViewModel.setTopRatedTv();
                if (tvShowsViewModel.getTopRatedTv().getValue() != null) {
                    topRatedAdapter.refillTv(tvShowsViewModel.getTopRatedTv().getValue().getTvShowItems());
                }
            }
        });

        tvShowsViewModel.getOnAirTv().observe(getViewLifecycleOwner(), tvShowResponse -> {
            if (tvShowResponse != null) {
                onTheAirAdapter.refillTv(tvShowResponse.getTvShowItems());
                hideShimmer(binding.playingShimmerContainer);
            } else {
                tvShowsViewModel.setOnAirTv();
                if (tvShowsViewModel.getOnAirTv().getValue() != null) {
                    onTheAirAdapter.refillTv(tvShowsViewModel.getOnAirTv().getValue().getTvShowItems());
                }
            }
        });

    }

    private void showShimmer() {
        binding.popularShimmerContainer.startShimmer();
        binding.ratedShimmerContainer.startShimmer();
        binding.playingShimmerContainer.startShimmer();
    }

    private void hideShimmer(ShimmerFrameLayout container) {
        container.hideShimmer();
        container.setVisibility(View.GONE);
    }

    private TvShowsAdapter.OnItemClicked onItemClicked = tvShow -> {
        TvShowFragmentDirections.ActionTvShowFragmentToTvShowDetailActivity action = TvShowFragmentDirections.actionTvShowFragmentToTvShowDetailActivity();
        action.setTvId(tvShow.getId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}