package com.ryanrvldo.moviecatalogue.data.injection;

import android.app.Application;

import com.ryanrvldo.moviecatalogue.data.Repository;
import com.ryanrvldo.moviecatalogue.data.source.local.LocalRepository;
import com.ryanrvldo.moviecatalogue.data.source.local.database.AppDatabase;
import com.ryanrvldo.moviecatalogue.data.source.remote.RemoteRepository;

public class DataInjection {
    public static Repository provideRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        LocalRepository localRepository = LocalRepository.getInstance(database.movieDao(), database.tvShowDao(), database.searchDao());
        RemoteRepository remoteRepository = RemoteRepository.getInstance();
        return Repository.getInstance(remoteRepository, localRepository);
    }
}