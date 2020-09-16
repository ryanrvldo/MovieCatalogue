package com.ryanrvldo.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.CastAdapter.CastViewHolder
import com.ryanrvldo.moviecatalogue.data.model.Cast
import com.ryanrvldo.moviecatalogue.databinding.ItemCastBinding

class CastAdapter(
    differCallback: DiffUtil.ItemCallback<Cast>
) : RecyclerView.Adapter<CastViewHolder>() {

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindCast(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    class CastViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindCast(cast: Cast) {
            binding.castName.text = cast.name
            binding.castCharacter.text = cast.character
            Glide.with(itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + cast.profilePath)
                .error(R.drawable.ic_undraw_avatar)
                .placeholder(R.drawable.ic_undraw_images)
                .override(120, 120)
                .into(binding.castPhoto)
        }
    }
}