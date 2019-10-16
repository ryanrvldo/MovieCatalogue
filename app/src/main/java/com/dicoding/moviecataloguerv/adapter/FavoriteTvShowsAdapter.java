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

import java.util.List;

public class FavoriteTvShowsAdapter extends RecyclerView.Adapter<FavoriteTvShowsAdapter.FavoriteTvViewHolder> {
    private List<TvShowItems> tvShowItems;
    private OnItemClicked onItemClicked;

    public FavoriteTvShowsAdapter(List<TvShowItems> tvShowItems, OnItemClicked onItemClicked) {
        this.tvShowItems = tvShowItems;
        this.onItemClicked = onItemClicked;
    }

    public void refillMovie(List<TvShowItems> items) {
        this.tvShowItems.clear();
        this.tvShowItems.addAll(items);
        notifyDataSetChanged();
    }

    public TvShowItems getTvAt(int position) {
        return tvShowItems.get(position);
    }

    @NonNull
    @Override
    public FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new FavoriteTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvViewHolder holder, int position) {
        holder.bind(tvShowItems.get(position));
    }


    @Override
    public int getItemCount() {
        return tvShowItems.size();
    }

    public interface OnItemClicked {
        void onItemClick(TvShowItems tvShowItems);
    }

    class FavoriteTvViewHolder extends RecyclerView.ViewHolder {
        TextView tvReleaseDate;
        TextView tvTitle;
        TextView tvRating;
        TextView tvGenre;
        ImageView tvPoster;
        TvShowItems tvShowItems;

        FavoriteTvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReleaseDate = itemView.findViewById(R.id.movie_release_date);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);
            tvPoster = itemView.findViewById(R.id.movie_poster);
            tvGenre = itemView.findViewById(R.id.movie_genre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onItemClick(tvShowItems);
                }
            });
        }

        private void bind(TvShowItems tvShowItems) {
            this.tvShowItems = tvShowItems;

            tvTitle.setText(tvShowItems.getTitle());
            tvGenre.setVisibility(View.GONE);
            tvRating.setText(String.valueOf(tvShowItems.getRating()));
            tvReleaseDate.setText(tvShowItems.getReleaseDate().split("-")[0]);
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + tvShowItems.getPosterPath())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(tvPoster);
        }

    }
}
