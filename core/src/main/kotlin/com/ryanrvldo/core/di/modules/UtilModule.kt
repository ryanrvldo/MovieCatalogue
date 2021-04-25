package com.ryanrvldo.core.di.modules

import com.ryanrvldo.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun provideSimpleDateFormat() = SimpleDateFormat(Constants.DATE_PATTERN, Locale.getDefault())

}
