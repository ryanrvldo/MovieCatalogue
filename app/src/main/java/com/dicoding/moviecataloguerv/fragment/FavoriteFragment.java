package com.dicoding.moviecataloguerv.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.dicoding.moviecataloguerv.activity.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.adapter.MoviesAdapter;
import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private RecyclerView movieFavorite;
    private TextView movieNullTV;
    private MoviesAdapter moviesAdapter;

    private RecyclerView tvShowsFavorite;
    private TextView tvShowNull;
    private TvShowsAdapter tvShowsAdapter;

    private FavoritesViewModel favoritesViewModel;
    private ProgressBar progressBar;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment_movie
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        showLoading(true);

        movieFavorite.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        moviesAdapter = new MoviesAdapter(new ArrayList<MovieItems>(), onMovieClicked, "favorite");
        movieFavorite.setAdapter(moviesAdapter);

        tvShowsFavorite.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvShowsAdapter = new TvShowsAdapter(new ArrayList<TvShowItems>(), onTvClicked, "favorite");
        tvShowsFavorite.setAdapter(tvShowsAdapter);

        if (getActivity() != null) {
            favoritesViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(Objects.requireNonNull(getActivity()).getApplication())).get(FavoritesViewModel.class);
        }
        observeData();
    }

    private void initUI(View view) {
        movieFavorite = view.findViewById(R.id.favorite_movies);
        movieNullTV = view.findViewById(R.id.movie_null);
        tvShowsFavorite = view.findViewById(R.id.favorite_tv);
        tvShowNull = view.findViewById(R.id.tv_null);
        progressBar = view.findViewById(R.id.progressBar);
        TextView movieTabTitle = view.findViewById(R.id.movie_tab_title);
        TextView movieTabDesc = view.findViewById(R.id.movie_tab_desc);
        TextView tvShowTabTitle = view.findViewById(R.id.tv_tab_title);
        TextView tvShowTabDesc = view.findViewById(R.id.tv_tab_desc);
        movieTabTitle.setText(getString(R.string.movies_tab));
        movieTabDesc.setText(getString(R.string.movie_favorite_tab_desc));
        tvShowTabTitle.setText(getString(R.string.tv_shows_tab));
        tvShowTabDesc.setText(getString(R.string.tv_favorite_tab_desc));
    }

    private void observeData() {
        if (getActivity() != null) {
            favoritesViewModel.getFavoriteMovies().observe(getActivity(), new Observer<List<MovieItems>>() {
                @Override
                public void onChanged(List<MovieItems> movieItems) {
                    if (movieItems != null) {
                        moviesAdapter.refillMovie(movieItems);
                        if (movieItems.size() == 0) {
                            movieNullTV.setVisibility(View.VISIBLE);
                        } else {
                            movieNullTV.setVisibility(View.GONE);
                        }
                    }
                }
            });

            favoritesViewModel.getFavoriteTvShows().observe(getActivity(), new Observer<List<TvShowItems>>() {
                @Override
                public void onChanged(List<TvShowItems> tvShowItems) {
                    if (tvShowItems != null) {
                        tvShowsAdapter.refillTv(tvShowItems);
                        if (tvShowItems.size() == 0) {
                            tvShowNull.setVisibility(View.VISIBLE);
                        } else {
                            tvShowNull.setVisibility(View.GONE);
                        }
                        showLoading(false);
                    }
                }
            });

            Log.d("FragmentFavorite", "Loaded");
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private MoviesAdapter.OnItemClicked onMovieClicked = new MoviesAdapter.OnItemClicked() {
        @Override
        public void onItemClick(MovieItems movieItems) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_ID, movieItems.getId());
            startActivity(intent);
        }
    };

    private TvShowsAdapter.OnItemClicked onTvClicked = new TvShowsAdapter.OnItemClicked() {
        @Override
        public void onItemClick(TvShowItems tvShowItems) {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShowItems.getId());
            startActivity(intent);
        }
    };

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new FavoriteFragment.LoadFavoriteMovieAsync(context).execute();
        }
    }

    private static class LoadFavoriteMovieAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;

        private LoadFavoriteMovieAsync(Context context) {
            weakContext = new WeakReference<>(context);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }
    }
}
