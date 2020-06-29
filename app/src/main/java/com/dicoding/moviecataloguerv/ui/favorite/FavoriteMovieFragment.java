package com.dicoding.moviecataloguerv.ui.favorite;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.dicoding.moviecataloguerv.adapter.MoviesAdapter;
import com.dicoding.moviecataloguerv.ui.detail.MovieDetailActivity;
import com.dicoding.moviecataloguerv.viewmodel.FavoritesViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView movieFavorite;
    private TextView movieNullTV;
    private MoviesAdapter moviesAdapter;

    private FavoritesViewModel favoritesViewModel;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    public FavoriteMovieFragment() {
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

        movieFavorite = view.findViewById(R.id.rvMovies);
        movieNullTV = view.findViewById(R.id.tv_null);
        progressBar = view.findViewById(R.id.progressBar);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        showLoading(true);

        movieFavorite.setHasFixedSize(true);
        movieFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        moviesAdapter = new MoviesAdapter(new ArrayList<>(), onMovieClicked, "favorite");
        movieFavorite.setAdapter(moviesAdapter);
        observeData();
    }

    public void observeData() {
        if (getActivity() != null) {
            favoritesViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FavoritesViewModel.class);
            favoritesViewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), movieItems -> {
                if (movieItems != null) {
                    moviesAdapter.refillMovie(movieItems);
                    if (movieItems.size() == 0) {
                        movieNullTV.setVisibility(View.VISIBLE);
                    } else {
                        movieNullTV.setVisibility(View.GONE);
                    }
                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            favoritesViewModel.deleteFavMovie(moviesAdapter.getMovieAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(getActivity(), moviesAdapter.getMovieAt(viewHolder.getAdapterPosition()).getTitle() + " deleted from movies favorite list.", Toast.LENGTH_SHORT).show();
                        }
                    }).attachToRecyclerView(movieFavorite);
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

    private MoviesAdapter.OnItemClicked onMovieClicked = movie -> {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.getId());
        startActivity(intent);
    };

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        favoritesViewModel.setFavoriteMovies();
        observeData();
        refreshLayout.setRefreshing(false);
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new FavoriteMovieFragment.LoadFavoriteMovieAsync(context).execute();
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
