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
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder> {

    private List<TvShow> tvShows;
    private List<Genre> genreList;
    private Context context;
    private OnItemClicked onItemClicked;

    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public TvShowsAdapter(List<TvShow> tvShows, Context context, List<Genre> genreList, OnItemClicked onItemClicked) {
        this.tvShows = tvShows;
        this.context = context;
        this.genreList = genreList;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv_show, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bind(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public interface OnItemClicked {
        void onItemClick(TvShow tvShow);
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvReleaseDate;
        TextView tvTitle;
        TextView tvRating;
        TextView tvGenres;
        ImageView tvPoster;
        TvShow tvShow;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReleaseDate = itemView.findViewById(R.id.tvShow_release_date);
            tvTitle = itemView.findViewById(R.id.tvShow_title);
            tvRating = itemView.findViewById(R.id.tvShow_rating);
            tvGenres = itemView.findViewById(R.id.tvShow_genre);
            tvPoster = itemView.findViewById(R.id.tvShow_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onItemClick(tvShow);
                }
            });
        }

        private void bind(TvShow tvShow) {
            this.tvShow = tvShow;

            tvReleaseDate.setText(tvShow.getReleaseDate().split("-")[0]);
            tvTitle.setText(tvShow.getTitle());
            tvRating.setText(String.valueOf(tvShow.getRating()));
            tvGenres.setText(getGenres(tvShow.getGenreIds()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + tvShow.getPosterPath())
                    .error(R.drawable.error)
                    .placeholder(R.drawable.placeholder)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(tvPoster);
        }

        private String getGenres(List<Integer> genreIds) {
            List<String> movieGenres = new ArrayList<>();
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

