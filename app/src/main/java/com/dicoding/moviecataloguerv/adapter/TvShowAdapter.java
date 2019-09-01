package com.dicoding.moviecataloguerv.adapter;

import android.content.Context;
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
import com.dicoding.moviecataloguerv.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private ArrayList<TvShow> tvShows;
    private Context context;
    private OnItemClicked onCLick;

    public TvShowAdapter(ArrayList<TvShow> list, Context context) {
        this.tvShows = list;
        this.context = context;
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid, viewGroup, false);
        return new TvShowViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, final int position) {
        TvShow tvShow = tvShows.get(position);

        Glide.with(holder.itemView.getContext())
                .load(tvShow.getPoster())
                .apply(new RequestOptions().override(500, 750))
                .into(holder.imgPoster);

        holder.tvTitle.setText(tvShow.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public void setOnCLick(OnItemClicked onCLick) {
        this.onCLick = onCLick;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.movie_poster);
            tvTitle = itemView.findViewById(R.id.movie_title);
        }
    }
}
