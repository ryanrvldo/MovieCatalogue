package com.ryanrvldo.moviecatalogue.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TvShowQualifier