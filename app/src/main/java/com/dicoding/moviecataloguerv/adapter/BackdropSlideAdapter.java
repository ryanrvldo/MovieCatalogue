package com.dicoding.moviecataloguerv.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.data.source.model.ImageItems;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class BackdropSlideAdapter extends SliderViewAdapter<BackdropSlideAdapter.BackdropSlideVH> {
    private List<ImageItems> backdropItems;

    public BackdropSlideAdapter(List<ImageItems> backdropItems) {
        if (backdropItems.size() > 8) {
            this.backdropItems = backdropItems.subList(0, 8);
        } else {
            this.backdropItems = backdropItems;
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public BackdropSlideVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new BackdropSlideVH(inflate);
    }

    @Override
    public void onBindViewHolder(BackdropSlideVH viewHolder, int position) {
        viewHolder.bind(backdropItems.get(position));
    }

    @Override
    public int getCount() {
        if (backdropItems != null) {
            return backdropItems.size();
        } else {
            return 0;
        }
    }

    static class BackdropSlideVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView backdropImage;

        BackdropSlideVH(View itemView) {
            super(itemView);
            backdropImage = itemView.findViewById(R.id.movieDetailsBackdrop);
            this.itemView = itemView;
        }

        private void bind(ImageItems imageItems) {
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + imageItems.getFilePath())
                    .error(R.drawable.ic_undraw_404)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_undraw_images).centerCrop())
                    .into(backdropImage);
        }
    }
}
