package com.ryanrvldo.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ryanrvldo.data.mapper.MovieMapper
import com.ryanrvldo.data.source.remote.MovieRemoteDataSource
import com.ryanrvldo.domain.model.movies.Movie
import com.ryanrvldo.domain.repository.MovieRepository
import com.ryanrvldo.domain.util.mapResultOf
import com.ryanrvldo.domain.util.resultOf
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteSource: MovieRemoteDataSource,
    private val mapper: MovieMapper
) : MovieRepository {

    override fun getMoviesByCategory(category: String): Flow<Result<PagingData<Movie>>> =
        remoteSource.getMoviesByCategory(category)
            .mapResultOf { it.map(mapper::mapMovieResponseToDomain) }

    override suspend fun getMovieDetails(id: Int) = resultOf { remoteSource.getMovieDetails(id) }
        .map(mapper::mapMovieDetailsResponseToDomain)

}