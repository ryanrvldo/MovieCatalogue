package com.dicoding.moviecataloguerv.fragment;


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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.MovieDetailActivity;
import com.dicoding.moviecataloguerv.adapter.MoviesAdapter;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.viewmodel.MoviesViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView popularRV;
    private MoviesAdapter popularAdapter;

    private RecyclerView topRatedRV;
    private MoviesAdapter topRatedAdapter;

    private RecyclerView nowPlayingRV;
    private MoviesAdapter nowPlayingAdapter;

    private MoviesViewModel moviesViewModel;
    private String language;

    private SkeletonScreen skeletonScreenPopular;
    private SkeletonScreen skeletonScreenTop;
    private SkeletonScreen skeletonScreenNow;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment_movie
        language = getResources().getString(R.string.language);
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);

        popularRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new MoviesAdapter(new ArrayList<MovieItems>(), onItemClicked, "movie");
        skeletonScreenPopular = Skeleton.bind(popularRV)
                .adapter(popularAdapter)
                .load(R.layout.item_movie_skeleton)
                .duration(1100)
                .show();

        topRatedRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedAdapter = new MoviesAdapter(new ArrayList<MovieItems>(), onItemClicked, "movie");
        skeletonScreenTop = Skeleton.bind(topRatedRV)
                .adapter(topRatedAdapter)
                .load(R.layout.item_movie_skeleton)
                .duration(1100)
                .show();

        nowPlayingRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingAdapter = new MoviesAdapter(new ArrayList<MovieItems>(), onItemClicked, "movie");
        skeletonScreenNow = Skeleton.bind(nowPlayingRV)
                .adapter(nowPlayingAdapter)
                .load(R.layout.item_movie_skeleton)
                .duration(1100)
                .show();
        if (getActivity() != null) {
            moviesViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
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
        nowPlayingRV = view.findViewById(R.id.now_playing_movies);
        popularTitle.setText(getString(R.string.popular));
        popularDesc.setText(getString(R.string.popular_desc));
        topRatedTitle.setText(getString(R.string.top_rated));
        topRatedDesc.setText(getString(R.string.top_rated_desc));
        nowPlayingTitle.setText(getString(R.string.now_playing));
        nowPlayingDesc.setText(getString(R.string.now_playing_desc));
    }

    private void observeData() {
        if (getActivity() != null) {
            moviesViewModel.getPopularMovies(language).observe(getActivity(), new Observer<MovieResponse>() {
                @Override
                public void onChanged(MovieResponse movieResponse) {
                    if (movieResponse != null) {
                        popularAdapter.refillMovie(movieResponse.getMovieItems());
                    }
                }
            });

            moviesViewModel.getTopRated(language).observe(getActivity(), new Observer<MovieResponse>() {
                @Override
                public void onChanged(MovieResponse movieResponse) {
                    if (movieResponse != null) {
                        topRatedAdapter.refillMovie(movieResponse.getMovieItems());
                    }
                }
            });

            moviesViewModel.getNowPlayingMovies(language).observe(getActivity(), new Observer<MovieResponse>() {
                @Override
                public void onChanged(MovieResponse movieResponse) {
                    if (movieResponse != null) {
                        nowPlayingAdapter.refillMovie(movieResponse.getMovieItems());
                        skeletonScreenPopular.hide();
                        skeletonScreenTop.hide();
                        skeletonScreenNow.hide();
                    }
                }
            });

            Log.d("FragmentPopularMovies", "Loaded");
        }
    }

    private MoviesAdapter.OnItemClicked onItemClicked = new MoviesAdapter.OnItemClicked() {
        @Override
        public void onItemClick(MovieItems movieItems) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_ID, movieItems.getId());
            startActivity(intent);
        }
    };
}
