package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.adapter.TvShowAdapter;
import com.dicoding.moviecataloguerv.model.TvShow;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements TvShowAdapter.OnItemClicked {

    private String[] tvShowTitle, tvShowDesc, tvShowRelease, tvShowPoster;
    private ArrayList<TvShow> tvShows;
    private TvShowAdapter adapter;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvTvShow = view.findViewById(R.id.recycler_view);
        rvTvShow.setHasFixedSize(true);

        rvTvShow.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new TvShowAdapter(tvShows, getActivity());
        rvTvShow.setAdapter(adapter);

        prepare();
        addData();

        adapter.setOnCLick(this);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailTvShow = new Intent(getActivity(), TvShowDetailActivity.class);
        detailTvShow.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShows.get(position));
        startActivity(detailTvShow);
    }

    private void addData() {
        tvShows = new ArrayList<>();

        for (int i = 0; i < tvShowTitle.length; i++) {
            TvShow tvShow = new TvShow();
            tvShow.setTitle(tvShowTitle[i]);
            tvShow.setPoster(tvShowPoster[i]);
            tvShow.setDesc(tvShowDesc[i]);
            tvShow.setRelease(tvShowRelease[i]);
            tvShows.add(tvShow);
        }
        adapter.setTvShows(tvShows);
    }

    private void prepare() {
        tvShowTitle = getResources().getStringArray(R.array.tvShow_title);
        tvShowDesc = getResources().getStringArray(R.array.tvShow_desc);
        tvShowRelease = getResources().getStringArray(R.array.tvShow_release);
        tvShowPoster = getResources().getStringArray(R.array.tvShow_poster);

    }

}
