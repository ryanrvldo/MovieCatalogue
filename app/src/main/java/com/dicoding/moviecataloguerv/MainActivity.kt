package com.dicoding.moviecataloguerv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moviecataloguerv.adapter.MoviesAdapter
import com.dicoding.moviecataloguerv.model.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularAdapter: MoviesAdapter
    private lateinit var popularLayoutManager: LinearLayoutManager
    private var popularPage = 1

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedAdapter: MoviesAdapter
    private lateinit var topRatedLayoutManager: LinearLayoutManager
    private var topRatedPage = 1

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingAdapter: MoviesAdapter
    private lateinit var upcomingLayoutManager: LinearLayoutManager
    private var upcomingPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMovies = findViewById(R.id.popular_movies)
        popularLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularMovies.layoutManager = popularLayoutManager
        popularAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularAdapter

        topRatedMovies = findViewById(R.id.top_rated_movies)
        topRatedLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatedMovies.layoutManager = topRatedLayoutManager
        topRatedAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        topRatedMovies.adapter = topRatedAdapter

        upcomingMovies = findViewById(R.id.upcoming_movies)
        upcomingLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        upcomingMovies.layoutManager = upcomingLayoutManager
        upcomingAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        upcomingMovies.adapter = upcomingAdapter

        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

    private fun getPopularMovies() {
        Repository.getPopularMovies(popularPage, ::onPopularMoviesFetched, ::onError)
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun getTopRatedMovies() {
        Repository.getTopRatedMovies(topRatedPage, ::onTopRatedMoviesFetched, ::onError)
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun getUpcomingMovies() {
        Repository.getUpcomingMovies(upcomingPage, ::onUpcomingMoviesFetched, ::onError)
    }

    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        upcomingAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularLayoutManager.itemCount //total movies in popularAdapter
                val visibleItemCount = popularLayoutManager.childCount
                val firstVisibleItem = popularLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    Log.d(localClassName, "Fetching popular movies")
                    popularPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedLayoutManager.itemCount
                val visibleItemCount = topRatedLayoutManager.childCount
                val firstVisibleItem = topRatedLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    Log.d(localClassName, "Fetching topRated Movies")
                    topRatedPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingLayoutManager.itemCount
                val visibleItemCount = upcomingLayoutManager.childCount
                val firstVisibleItem = upcomingLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    Log.d(localClassName, "Fetching upcoming Movies")
                    upcomingPage++
                    getUpcomingMovies()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}