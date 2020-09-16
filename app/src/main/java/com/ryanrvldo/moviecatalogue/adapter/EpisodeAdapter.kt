package com.ryanrvldo.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.EpisodeAdapter.EpisodeViewHolder
import com.ryanrvldo.moviecatalogue.data.model.Episode
import com.ryanrvldo.moviecatalogue.databinding.ItemEpisodeBinding
import java.text.SimpleDateFormat
import java.util.*

class EpisodeAdapter(
    diffCallback: DiffUtil.ItemCallback<Episode>
) : RecyclerView.Adapter<EpisodeViewHolder>() {

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindItem(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class EpisodeViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(episode: Episode) {
            binding.apply {
                val newDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    .parse(episode.airDate)
                newDate?.let {
                    episodeRelease.text = SimpleDateFormat("dd MMM yyyy", Locale.US)
                        .format(newDate)
                }
                episodeTitle.text = episode.name
                episodeOverview.text = episode.overview
                episodeNumber.text = episode.number.toString()
                Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + episode.stillPath)
                    .error(R.drawable.ic_undraw_404)
                    .placeholder(R.drawable.ic_undraw_images)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(episodePoster)
            }
        }
    }

}