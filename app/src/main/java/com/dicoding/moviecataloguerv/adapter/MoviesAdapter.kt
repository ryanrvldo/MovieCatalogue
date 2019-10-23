package com.dicoding.moviecataloguerv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.dicoding.moviecataloguerv.model.Movie
import com.dicoding.moviecataloguerv.BuildConfig
import com.dicoding.moviecataloguerv.R

class MoviesAdapter(
        private var movies: MutableList<Movie>,
        private val onMovieClick: (movie: Movie) -> Unit
) :
        RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(this.movies.size, movies.size - 1)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        fun bind(movie: Movie) {
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movie.posterPath)
                    .transform(CenterCrop())
                    .into(poster)
            itemView.setOnClickListener {
                onMovieClick.invoke(movie)
            }
        }
    }

}