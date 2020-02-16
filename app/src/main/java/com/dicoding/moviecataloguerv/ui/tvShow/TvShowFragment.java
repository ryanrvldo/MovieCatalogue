package com.dicoding.moviecataloguerv.ui.tvShow;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.ui.detail.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.viewmodel.TvShowsViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView popularRV;
    private TvShowsAdapter popularAdapter;

    private RecyclerView topRatedRV;
    private TvShowsAdapter topRatedAdapter;

    private RecyclerView onTheAirRV;
    private TvShowsAdapter onTheAirAdapter;

    private TvShowsViewModel tvShowsViewModel;

    private SkeletonScreen skeletonScreenPopular;
    private SkeletonScreen skeletonScreenTop;
    private SkeletonScreen skeletonScreenOnAir;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);

        popularRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new TvShowsAdapter(new ArrayList<>(), onItemClicked, "tvShow");
        skeletonScreenPopular = Skeleton.bind(popularRV)
                .adapter(popularAdapter)
                .load(R.layout.item_movie_skeleton)
                .duration(1100)
                .show();

        topRatedRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedAdapter = new TvShowsAdapter(new ArrayList<>(), onItemClicked, "tvShow");
        skeletonScreenTop = Skeleton.bind(topRatedRV)
                .adapter(topRatedAdapter)
                .load(R.layout.item_movie_skeleton)
                .duration(1100)
                .show();

        onTheAirRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        onTheAirAdapter = new TvShowsAdapter(new ArrayList<>(), onItemClicked, "tvShow");
        onTheAirRV.setAdapter(onTheAirAdapter);
        skeletonScreenOnAir = Skeleton.bind(onTheAirRV)
                .adapter(onTheAirAdapter)
                .load(R.layout.item_movie_skeleton)
                .duration(1100)
                .show();
        if (getActivity() != null) {
            tvShowsViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        }
        observeData();
    }

    private void initUI(View view) {
        TextView popularTitle = view.findViewById(R.id.popular_title);
        TextView popularDesc = view.findViewById(R.id.popular_desc);
        TextView topRatedTitle = view.findViewById(R.id.top_rated_title);
        TextView topRatedDesc = view.findViewById(R.id.top_rated_desc);
        TextView nowPlayingTitle = view.findViewById(R.id.now_playing_title);
        TextView nowPlayingDesc = view.findViewById(R.id.now_playing_desc);
        popularRV = view.findViewById(R.id.popular_movies);
        topRatedRV = view.findViewById(R.id.top_rated_movies);
        onTheAirRV = view.findViewById(R.id.now_playing_movies);
        popularTitle.setText(getString(R.string.popular));
        popularDesc.setText(getString(R.string.popular_tv_desc));
        topRatedTitle.setText(getString(R.string.top_rated));
        topRatedDesc.setText(getString(R.string.top_rated_tv_desc));
        nowPlayingTitle.setText(getString(R.string.on_the_air));
        nowPlayingDesc.setText(getString(R.string.on_the_air_desc));
    }

    private void observeData() {
//        if (getActivity() != null) {
            tvShowsViewModel.getPopularTv().observe(getViewLifecycleOwner(), tvShowResponse -> {
                if (tvShowResponse != null) {
                    popularAdapter.refillTv(tvShowResponse.getTvShowItems());
                    skeletonScreenPopular.hide();
                } else {
                    tvShowsViewModel.setPopularTv();
                    if (tvShowsViewModel.getPopularTv().getValue() != null) {
                        popularAdapter.refillTv(tvShowsViewModel.getPopularTv().getValue().getTvShowItems());
                        skeletonScreenPopular.hide();
                    }
                }
            });

            tvShowsViewModel.getTopRatedTv().observe(getViewLifecycleOwner(), tvShowResponse -> {
                if (tvShowResponse != null) {
                    topRatedAdapter.refillTv(tvShowResponse.getTvShowItems());
                    skeletonScreenTop.hide();
                } else {
                    tvShowsViewModel.setTopRatedTv();
                    if (tvShowsViewModel.getTopRatedTv().getValue() != null) {
                        topRatedAdapter.refillTv(tvShowsViewModel.getTopRatedTv().getValue().getTvShowItems());
                        skeletonScreenTop.hide();
                    }
                }
            });

            tvShowsViewModel.getOnAirTv().observe(getViewLifecycleOwner(), tvShowResponse -> {
                if (tvShowResponse != null) {
                    onTheAirAdapter.refillTv(tvShowResponse.getTvShowItems());
                    skeletonScreenOnAir.hide();
                } else {
                    tvShowsViewModel.setOnAirTv();
                    if (tvShowsViewModel.getOnAirTv().getValue() != null) {
                        onTheAirAdapter.refillTv(tvShowsViewModel.getOnAirTv().getValue().getTvShowItems());
                        skeletonScreenOnAir.hide();
                    }
                }
            });

            Log.d("FragmentTV", "Loaded");
//        }
    }

    private TvShowsAdapter.OnItemClicked onItemClicked = tvShow -> {
        Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
        intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShow.getId());
        startActivity(intent);
    };

}