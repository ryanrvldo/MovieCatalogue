package com.dicoding.moviecataloguerv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.TvShowItems;

import java.util.ArrayList;
import java.util.List;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder> {

    private List<TvShowItems> tvShowItems;
    private OnItemClicked onItemClicked;
    private String type;

    public TvShowsAdapter(ArrayList<TvShowItems> tvShowItems, OnItemClicked onItemClicked, String type) {
        this.tvShowItems = tvShowItems;
        this.onItemClicked = onItemClicked;
        this.type = type;
    }

    public void refillTv(List<TvShowItems> items) {
        this.tvShowItems.clear();
        this.tvShowItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (type.equalsIgnoreCase("tvShow")) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
            return new TvShowViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, viewGroup, false);
            return new TvShowViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        if (type.equalsIgnoreCase("tvShow")) {
            holder.bindTv(tvShowItems.get(position));
        } else {
            holder.bindSearch(tvShowItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return tvShowItems.size();
    }

    public interface OnItemClicked {
        void onItemClick(TvShowItems tvShowItems);
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvRating;
        ImageView tvPoster;
        TextView tvOverview;
        TvShowItems tvShowItems;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);
            tvPoster = itemView.findViewById(R.id.movie_poster);
            tvOverview = itemView.findViewById(R.id.movie_overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onItemClick(tvShowItems);
                }
            });
        }

        private void bindTv(TvShowItems tvShowItems) {
            this.tvShowItems = tvShowItems;

            tvTitle.setText(tvShowItems.getTitle());
            tvRating.setText(String.valueOf(tvShowItems.getRating()));
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + tvShowItems.getPosterPath())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(tvPoster);
        }

        private void bindSearch(TvShowItems tvShowItems) {
            this.tvShowItems = tvShowItems;

            tvTitle.setText(tvShowItems.getTitle());
            tvRating.setText(String.valueOf(tvShowItems.getRating()));
            tvOverview.setText(tvShowItems.getOverview());
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + tvShowItems.getBackdrop())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .into(tvPoster);
        }
    }
}

