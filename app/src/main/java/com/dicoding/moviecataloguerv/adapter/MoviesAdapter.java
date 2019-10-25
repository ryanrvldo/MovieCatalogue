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
import com.dicoding.moviecataloguerv.model.MovieItems;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<MovieItems> movieItems;
    private OnItemClicked onItemClicked;
    private String type;

    public MoviesAdapter(ArrayList<MovieItems> movieItems, OnItemClicked onItemClicked, String type) {
        this.movieItems = movieItems;
        this.onItemClicked = onItemClicked;
        this.type = type;
    }

    public void refillMovie(List<MovieItems> items) {
        this.movieItems.clear();
        this.movieItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (type.equalsIgnoreCase("movie")) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
            return new MovieViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, viewGroup, false);
            return new MovieViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        if (type.equalsIgnoreCase("movie")) {
            holder.bindMovie(movieItems.get(position));
        } else {
            holder.bindSearch(movieItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public interface OnItemClicked {
        void onItemClick(MovieItems movieItems);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvRating;
        ImageView tvPoster;
        TextView tvOverview;
        MovieItems movieItems;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);
            tvPoster = itemView.findViewById(R.id.movie_poster);
            tvOverview = itemView.findViewById(R.id.movie_overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onItemClick(movieItems);
                }
            });
        }

        private void bindMovie(MovieItems movieItems) {
            this.movieItems = movieItems;

            tvTitle.setText(movieItems.getTitle());
            tvRating.setText(String.valueOf(movieItems.getRating()));
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movieItems.getPosterPath())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(tvPoster);
        }

        private void bindSearch(MovieItems movieItems) {
            this.movieItems = movieItems;

            tvTitle.setText(movieItems.getTitle());
            tvRating.setText(String.valueOf(movieItems.getRating()));
            tvOverview.setText(movieItems.getOverview());
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movieItems.getBackdrop())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .into(tvPoster);
        }
    }
}
