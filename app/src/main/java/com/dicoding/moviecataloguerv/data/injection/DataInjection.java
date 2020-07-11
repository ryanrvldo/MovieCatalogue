package com.dicoding.moviecataloguerv.data.injection;

import android.app.Application;

import com.dicoding.moviecataloguerv.data.Repository;
import com.dicoding.moviecataloguerv.data.source.local.LocalRepository;
import com.dicoding.moviecataloguerv.data.source.local.database.AppDatabase;
import com.dicoding.moviecataloguerv.data.source.remote.RemoteRepository;

public class DataInjection {
    public static Repository provideRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        LocalRepository localRepository = LocalRepository.getInstance(database.movieDao(), database.tvShowDao(), database.searchDao());
        RemoteRepository remoteRepository = RemoteRepository.getInstance();
        return Repository.getInstance(remoteRepository, localRepository);
    }
}
