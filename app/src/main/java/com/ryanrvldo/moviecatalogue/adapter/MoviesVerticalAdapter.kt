package com.ryanrvldo.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.MoviesVerticalAdapter.MoviesVerticalVH
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.databinding.ItemMovieVerticalBinding

class MoviesVerticalAdapter(
    private val callback: OnItemClicked<Movie>,
    differCallback: DiffUtil.ItemCallback<Movie>,
) : RecyclerView.Adapter<MoviesVerticalVH>() {

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesVerticalVH =
        MoviesVerticalVH(
            ItemMovieVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MoviesVerticalVH, position: Int) {
        holder.bindMovieItem(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MoviesVerticalVH(private val binding: ItemMovieVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMovieItem(movie: Movie) {
            binding.apply {
                movieTitle.text = movie.title
                movieRating.text = movie.rating.toString()
                movieOverview.text = movie.overview
                Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movie.backdrop)
                    .placeholder(R.drawable.ic_undraw_images)
                    .error(R.drawable.ic_undraw_404).centerCrop()
                    .into(moviePoster)

                root.setOnClickListener {
                    callback.onItemClicked(movie)
                }
            }
        }

    }
}