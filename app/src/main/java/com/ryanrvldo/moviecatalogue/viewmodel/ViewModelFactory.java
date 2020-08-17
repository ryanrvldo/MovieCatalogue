package com.ryanrvldo.moviecatalogue.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ryanrvldo.moviecatalogue.data.Repository;
import com.ryanrvldo.moviecatalogue.data.injection.DataInjection;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory mInstance;
    private final Repository mRepository;

    private ViewModelFactory(Repository repository) {
        this.mRepository = repository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (mInstance == null) {
            synchronized (ViewModelFactory.class) {
                if (mInstance == null) {
                    mInstance = new ViewModelFactory(DataInjection.provideRepository(application));
                }
            }
        }
        return mInstance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
            // noinspection unchecked
            return (T) new MoviesViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(MovieDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieDetailViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(TvShowsViewModel.class)) {
            //noinspection unchecked
            return (T) new TvShowsViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(TvShowDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new TvShowDetailViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(SeasonsViewModel.class)) {
            //noinspection unchecked
            return (T) new SeasonsViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(FavoritesViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoritesViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(RecentSearchViewModel.class)) {
            //noinspection unchecked
            return (T) new RecentSearchViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            //noinspection unchecked
            return (T) new SearchViewModel(mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
