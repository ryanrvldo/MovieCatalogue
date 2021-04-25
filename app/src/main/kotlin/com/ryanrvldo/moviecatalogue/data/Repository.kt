package com.ryanrvldo.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryanrvldo.moviecatalogue.data.local.LocalRepository
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.model.Search
import com.ryanrvldo.moviecatalogue.data.model.Season
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.data.remote.LoadDataCallback
import com.ryanrvldo.moviecatalogue.data.remote.RemoteRepository
import com.ryanrvldo.moviecatalogue.data.remote.response.MovieResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.TvShowResponse
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.utils.performGetOperation
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
) : DataSource {

    override fun getMovies(category: String) = performGetOperation {
        remoteRepository.getMovies(category)
    }

    override fun getMovieDetails(movieId: Int): LiveData<Resource<Movie>> = performGetOperation {
        remoteRepository.getMovieDetails(movieId)
    }

    override fun searchMovies(query: String): LiveData<Resource<MovieResponse>> =
        performGetOperation {
            remoteRepository.searchMovies(query)
        }

    override fun getNewReleaseMovies(): LiveData<MovieResponse> {
        val response = MutableLiveData<MovieResponse>()
        remoteRepository.getNewReleaseMovies(object : LoadDataCallback<MovieResponse> {
            override fun onDataReceived(data: MovieResponse) {
                response.postValue(data)
            }

            override fun onDataNotAvailable() {
                Timber.d("onDataNotAvailable")
            }
        })
        return response
    }

    override fun getTvShows(category: String): LiveData<Resource<TvShowResponse>> =
        performGetOperation { remoteRepository.getTvShows(category) }

    override fun getTvShowDetails(tvShowId: Int): LiveData<Resource<TvShow>> = performGetOperation {
        remoteRepository.getTvDetail(tvShowId)
    }

    override fun searchTvShows(query: String): LiveData<Resource<TvShowResponse>> =
        performGetOperation { remoteRepository.searchTvShows(query) }

    override fun getNewReleaseTvShows(): LiveData<TvShowResponse> {
        val response = MutableLiveData<TvShowResponse>()
        remoteRepository.getNewReleaseTvShows(object : LoadDataCallback<TvShowResponse> {
            override fun onDataReceived(data: TvShowResponse) {
                response.postValue(data)
            }

            override fun onDataNotAvailable() {
                Timber.tag("NewReleaseTvShows").d("onDataNotAvailable")
            }
        })
        return response
    }

    override fun getSeasonDetails(tvId: Int, seasonNumber: Int): LiveData<Season> {
        val response = MutableLiveData<Season>()
        remoteRepository.getSeasonDetail(tvId, seasonNumber, object : LoadDataCallback<Season> {
            override fun onDataReceived(data: Season) {
                response.postValue(data)
            }

            override fun onDataNotAvailable() {
                Timber.d("onDataNotAvailable")
            }
        })
        return response
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> = localRepository.getFavoriteMovies()

    override suspend fun addFavoriteMovie(movie: Movie) = localRepository.addMovie(movie)

    override suspend fun removeFavoriteMovie(movie: Movie) = localRepository.removeMovie(movie)

    override suspend fun isFavoriteMovie(movieId: Int): Boolean =
        localRepository.isMovieExists(movieId)

    override fun getFavoriteTvShows(): LiveData<List<TvShow>> = localRepository.getFavoriteTvShows()

    override suspend fun addFavoriteTvShow(tvShow: TvShow) = localRepository.addTvShow(tvShow)

    override suspend fun removeFavoriteTvShow(tvShow: TvShow) = localRepository.removeTvShow(tvShow)

    override suspend fun isFavoriteTvShow(tvShowId: Int): Boolean =
        localRepository.isTvShowExists(tvShowId)

    override fun getSearchHistories(): LiveData<List<Search>> = localRepository.getSearchHistories()

    override suspend fun addSearchQuery(search: Search) = localRepository.addSearchQuery(search)

    override suspend fun removeSearchQuery(search: Search) =
        localRepository.removeSearchQuery(search)

    override suspend fun removeSearchHistories() = localRepository.removeSearchHistories()

    override suspend fun selectSearchQuery(query: String): String? =
        localRepository.getSearch(query)
}
