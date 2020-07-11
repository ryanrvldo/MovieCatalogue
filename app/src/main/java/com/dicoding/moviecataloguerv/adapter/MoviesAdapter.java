package com.dicoding.moviecataloguerv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.data.source.model.Movie;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movieItems;
    private OnItemClicked onItemClicked;
    private String type;

    public MoviesAdapter(ArrayList<Movie> movieItems, OnItemClicked onItemClicked, String type) {
        this.movieItems = movieItems;
        this.onItemClicked = onItemClicked;
        this.type = type;
    }

    public void refillMovie(List<Movie> items) {
        this.movieItems.clear();
        this.movieItems.addAll(items);
        notifyDataSetChanged();
    }

    public Movie getMovieAt(int position) {
        return movieItems.get(position);
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
        void onItemClick(Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvRating;
        ShapeableImageView tvPoster;
        TextView tvOverview;
        Movie movie;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);
            tvPoster = itemView.findViewById(R.id.movie_poster);
            tvOverview = itemView.findViewById(R.id.movie_overview);

            itemView.setOnClickListener(v -> onItemClicked.onItemClick(movie));
        }

        private void bindMovie(Movie movie) {
            this.movie = movie;

            tvTitle.setText(movie.getTitle());
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.ic_undraw_images)
                    .error(R.drawable.ic_undraw_404).centerCrop().transition(withCrossFade())
                    .into(tvPoster);
        }

        private void bindSearch(Movie movie) {
            this.movie = movie;

            tvTitle.setText(movie.getTitle());
            tvRating.setText(String.valueOf(movie.getRating()));
            tvOverview.setText(movie.getOverview());
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movie.getBackdrop())
                    .placeholder(R.drawable.ic_undraw_images)
                    .error(R.drawable.ic_undraw_404).centerCrop().transition(withCrossFade())
                    .into(tvPoster);
        }
    }
}
