package com.dicoding.moviecataloguerv.adapter;

import android.text.TextUtils;
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
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.MovieItems;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private ArrayList<MovieItems> movieItems;
    private ArrayList<Genre> genreList;
    private OnItemClicked onItemClicked;

    public MoviesAdapter(ArrayList<MovieItems> movieItems, ArrayList<Genre> genreList, OnItemClicked onItemClicked) {
        this.movieItems = movieItems;
        this.genreList = genreList;
        this.onItemClicked = onItemClicked;
    }

    public void refillMovie(ArrayList<MovieItems> items) {
        this.movieItems = new ArrayList<>();
        this.movieItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clearMovie() {
        this.movieItems.clear();
        notifyDataSetChanged();
    }

    public void refillGenre(ArrayList<Genre> genres) {
        this.genreList = new ArrayList<>();
        this.genreList.addAll(genres);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        holder.bind(movieItems.get(position));
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public interface OnItemClicked {
        void onItemClick(MovieItems movieItems);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvReleaseDate;
        TextView tvTitle;
        TextView tvRating;
        TextView tvGenres;
        ImageView tvPoster;
        MovieItems movieItems;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReleaseDate = itemView.findViewById(R.id.movie_release_date);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);
            tvGenres = itemView.findViewById(R.id.movie_genre);
            tvPoster = itemView.findViewById(R.id.movie_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onItemClick(movieItems);
                }
            });
        }

        private void bind(MovieItems movieItems) {
            this.movieItems = movieItems;

            tvReleaseDate.setText(movieItems.getReleaseDate().split("-")[0]);
            tvTitle.setText(movieItems.getTitle());
            tvRating.setText(String.valueOf(movieItems.getRating()));
            tvGenres.setText(getGenres(movieItems.getGenreIds()));
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + movieItems.getPosterPath())
                    .error(R.drawable.error)
                    .placeholder(R.drawable.placeholder)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(tvPoster);
        }

        private String getGenres(ArrayList<Integer> genreIds) {
            ArrayList<String> movieGenres = new ArrayList<>();
            for (Integer genreId : genreIds) {
                for (Genre genre : genreList) {
                    if (genre.getId() == genreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }
    }
}
