package com.ryanrvldo.moviecatalogue.data.source.local;

import androidx.lifecycle.LiveData;

import com.ryanrvldo.moviecatalogue.data.source.local.database.MovieDao;
import com.ryanrvldo.moviecatalogue.data.source.local.database.SearchDao;
import com.ryanrvldo.moviecatalogue.data.source.local.database.TvShowDao;
import com.ryanrvldo.moviecatalogue.data.source.model.Movie;
import com.ryanrvldo.moviecatalogue.data.source.model.Search;
import com.ryanrvldo.moviecatalogue.data.source.model.TvShow;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalRepository {
    private static final int FIXED_THREAD = 4;
    private static volatile LocalRepository mInstance;
    private final ExecutorService mExecutor = Executors.newFixedThreadPool(FIXED_THREAD);
    private MovieDao movieDao;
    private TvShowDao tvShowDao;
    private SearchDao searchDao;

    public LocalRepository(MovieDao mDao, TvShowDao tvDao, SearchDao sDao) {
        this.movieDao = mDao;
        this.tvShowDao = tvDao;
        this.searchDao = sDao;
    }

    public static LocalRepository getInstance(MovieDao mDao, TvShowDao tvDao, SearchDao sDao) {
        if (mInstance == null) {
            mInstance = new LocalRepository(mDao, tvDao, sDao);
        }
        return mInstance;
    }

    public LiveData<List<Movie>> getAllFavMovies() {
        return movieDao.getAllFavoriteMovie();
    }

    public void insertMovie(Movie movie) {
        mExecutor.execute(() -> movieDao.insert(movie));
    }

    public void deleteMovie(Movie movie) {
        mExecutor.execute(() -> movieDao.delete(movie));
    }

    public Movie getMovie(int id) {
        try {
            return mExecutor.submit(() -> movieDao.selectById(id)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LiveData<List<TvShow>> getAllFavTv() {
        return tvShowDao.getAllFavoriteTv();
    }

    public void insertTv(TvShow tvShow) {
        mExecutor.execute(() -> tvShowDao.insert(tvShow));
    }

    public void deleteTv(TvShow tvShow) {
        mExecutor.execute(() -> tvShowDao.delete(tvShow));
    }

    public TvShow getTvShow(int id) {
        try {
            return mExecutor.submit(() -> tvShowDao.selectById(id)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LiveData<List<Search>> getAllSearch() {
        return searchDao.getAllSearch();
    }

    public void insertSearch(Search search) {
        mExecutor.execute(() -> searchDao.insert(search));
    }

    public void deleteSearch(Search search) {
        mExecutor.execute(() -> searchDao.delete(search));
    }

    public void deleteAllSearch() {
        mExecutor.execute(() -> searchDao.deleteAllSearch());
    }

    public String getSearch(String query) {
        try {
            return mExecutor.submit(() -> searchDao.getSearch(query)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
