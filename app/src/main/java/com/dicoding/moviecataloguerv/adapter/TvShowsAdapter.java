package com.dicoding.moviecataloguerv.adapter;

import android.content.Context;
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
import com.dicoding.moviecataloguerv.model.TvShowItems;

import java.util.ArrayList;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder> {

    private ArrayList<TvShowItems> tvShowItems;
    private ArrayList<Genre> genreList;
    private OnItemClicked onItemClicked;

    public TvShowsAdapter(ArrayList<TvShowItems> tvShowItems, ArrayList<Genre> genreList, OnItemClicked onItemClicked) {
        this.tvShowItems = tvShowItems;
        this.genreList = genreList;
        this.onItemClicked = onItemClicked;
    }

    public void refillTv(ArrayList<TvShowItems> items) {
        this.tvShowItems = new ArrayList<>();
        this.tvShowItems.addAll(items);
        notifyDataSetChanged();
    }

    public void refillGenre(ArrayList<Genre> items) {
        this.genreList = new ArrayList<>();
        this.genreList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearTv() {
        this.tvShowItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bind(tvShowItems.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowItems.size();
    }

    public interface OnItemClicked {
        void onItemClick(TvShowItems tvShowItems);
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvReleaseDate;
        TextView tvTitle;
        TextView tvRating;
        TextView tvGenres;
        ImageView tvPoster;
        TvShowItems tvShowItems;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReleaseDate = itemView.findViewById(R.id.movie_release_date);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);
            tvGenres = itemView.findViewById(R.id.movie_genre);
            tvPoster = itemView.findViewById(R.id.movie_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onItemClick(tvShowItems);
                }
            });
        }

        private void bind(TvShowItems tvShowItems) {
            this.tvShowItems = tvShowItems;

            tvReleaseDate.setText(tvShowItems.getReleaseDate().split("-")[0]);
            tvTitle.setText(tvShowItems.getTitle());
            tvRating.setText(String.valueOf(tvShowItems.getRating()));
            tvGenres.setText(getGenres(tvShowItems.getGenreIds()));
            Glide.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + tvShowItems.getPosterPath())
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

