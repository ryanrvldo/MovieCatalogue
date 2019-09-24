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
    private Context context;
    private OnItemClicked onItemClicked;

    public TvShowsAdapter(ArrayList<TvShowItems> tvShowItems, Context context, ArrayList<Genre> genreList, OnItemClicked onItemClicked) {
        this.tvShowItems = tvShowItems;
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
            tvReleaseDate = itemView.findViewById(R.id.tvShow_release_date);
            tvTitle = itemView.findViewById(R.id.tvShow_title);
            tvRating = itemView.findViewById(R.id.tvShow_rating);
            tvGenres = itemView.findViewById(R.id.tvShow_genre);
            tvPoster = itemView.findViewById(R.id.tvShow_poster);

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

