package com.ryanrvldo.moviecatalogue.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.data.model.Image
import com.smarteist.autoimageslider.SliderViewAdapter

class BackdropSlideAdapter(
    private val images: List<Image>
) : SliderViewAdapter<BackdropSlideAdapter.BackdropSlideVH>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup): BackdropSlideVH {
        return BackdropSlideVH(
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        )
    }

    override fun onBindViewHolder(viewHolder: BackdropSlideVH, position: Int) {
        viewHolder.bind(images[position])
    }

    override fun getCount(): Int = images.size

    class BackdropSlideVH(private val itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        fun bind(image: Image) {
            Glide.with(itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + image.filePath)
                .error(R.drawable.ic_undraw_404)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_undraw_images).centerCrop())
                .into(itemView.findViewById(R.id.movieDetailsBackdrop))
        }
    }
}