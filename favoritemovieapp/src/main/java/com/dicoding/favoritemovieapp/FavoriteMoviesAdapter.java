package com.dicoding.favoritemovieapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMovieViewHolder> {
    private final ArrayList<MovieFavorite> movieItems = new ArrayList<>();
    private final Activity activity;

    public FavoriteMoviesAdapter(Activity activity) {
        this.activity = activity;
    }

    public void refillMovie(ArrayList<MovieFavorite> items) {
        this.movieItems.clear();
        this.movieItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, int position) {
        holder.bind(movieItems.get(position));
    }


    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvReleaseDate;
        TextView tvTitle;
        TextView tvRating;
        TextView tvGenre;
        ImageView tvPoster;
        MovieFavorite movieItems;

        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReleaseDate = itemView.findViewById(R.id.movie_release_date);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);
            tvPoster = itemView.findViewById(R.id.movie_poster);
            tvGenre = itemView.findViewById(R.id.movie_genre);
        }

        private void bind(MovieFavorite movieItems) {
            this.movieItems = movieItems;

            tvReleaseDate.setText(movieItems.getReleaseDate().split("-")[0]);
            tvTitle.setText(movieItems.getTitle());
            tvGenre.setVisibility(View.GONE);
            tvRating.setText(String.valueOf(movieItems.getVoteAverage()));
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movieItems.getPosterPath())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_image)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(tvPoster);
        }

    }
}
