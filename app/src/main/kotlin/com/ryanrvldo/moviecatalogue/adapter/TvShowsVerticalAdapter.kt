package com.ryanrvldo.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.TvShowsVerticalAdapter.TvShowsVerticalVH
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.databinding.ItemMovieVerticalBinding

class TvShowsVerticalAdapter(
    private val callback: OnItemClicked<TvShow>,
    itemCallback: DiffUtil.ItemCallback<TvShow>
) : RecyclerView.Adapter<TvShowsVerticalVH>() {

    val differ = AsyncListDiffer(this, itemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsVerticalVH =
        TvShowsVerticalVH(
            ItemMovieVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TvShowsVerticalVH, position: Int) {
        holder.bindItem(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class TvShowsVerticalVH(private val binding: ItemMovieVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(tvShow: TvShow) {
            binding.movieTitle.text = tvShow.title
            binding.movieRating.text = tvShow.rating.toString()
            binding.movieOverview.text = tvShow.overview
            Glide.with(itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + tvShow.backdrop)
                .placeholder(R.drawable.ic_undraw_images)
                .error(R.drawable.ic_undraw_404).centerCrop()
                .into(binding.moviePoster)

            itemView.setOnClickListener {
                callback.onItemClicked(tvShow)
            }
        }
    }
}