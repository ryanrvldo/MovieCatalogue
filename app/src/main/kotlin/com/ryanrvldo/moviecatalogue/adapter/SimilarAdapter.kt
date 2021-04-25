package com.ryanrvldo.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.SimilarAdapter.SimilarViewHolder
import com.ryanrvldo.moviecatalogue.data.model.Similar
import com.ryanrvldo.moviecatalogue.databinding.ItemSimilarBinding
import com.ryanrvldo.moviecatalogue.utils.Constants.MOVIE_TYPE
import com.ryanrvldo.moviecatalogue.utils.Constants.TV_TYPE

// TODO: 9/1/20 Add to adapter module
class SimilarAdapter(
    private val similarList: MutableList<Similar>,
    private val type: String,
    private val onItemClicked: OnItemClicked<Similar>
) : RecyclerView.Adapter<SimilarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        return SimilarViewHolder(
            ItemSimilarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.bindSimilarItem(similarList[position])
    }

    override fun getItemCount(): Int = similarList.size

    fun setSimilarList(similarList: List<Similar>) {
        this.similarList.clear()
        this.similarList.addAll(similarList)
        notifyDataSetChanged()
    }

    inner class SimilarViewHolder(private val binding: ItemSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindSimilarItem(similar: Similar) {
            if (type == MOVIE_TYPE) {
                binding.titleSimilar.text = similar.title
            } else if (type == TV_TYPE) {
                binding.titleSimilar.text = similar.name
            }

            binding.ratingSimilar.text = similar.rating.toString()
            Glide.with(itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + similar.posterPath)
                .placeholder(R.drawable.ic_undraw_images)
                .error(R.drawable.ic_undraw_404)
                .centerCrop()
                .into(binding.posterSimilar)
            binding.root.setOnClickListener {
                onItemClicked.onItemClicked(similar)
            }
        }
    }
}