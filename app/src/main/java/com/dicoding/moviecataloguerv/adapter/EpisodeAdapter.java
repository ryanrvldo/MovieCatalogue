package com.dicoding.moviecataloguerv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.data.source.model.Episode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.SeasonViewHolder> {

    private List<Episode> episodes;

    public EpisodeAdapter(List<Episode> episodeList) {
        this.episodes = episodeList;
    }

    public void setEpisodes(List<Episode> episodeList) {
        episodes.clear();
        episodes.addAll(episodeList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {
        Episode episode = episodes.get(position);
        Glide.with(holder.itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + episode.getStillPath())
                .error(R.drawable.ic_undraw_404)
                .placeholder(R.drawable.ic_undraw_images)
                .transition(withCrossFade())
                .into(holder.episodePoster);
        holder.episodeName.setText(episode.getName());
        holder.episodeDescription.setText(episode.getOverview());
        holder.episodeNumber.setText(String.valueOf(episode.getNumber()));
        if (episode.getAirDate() != null) {
            String date = episode.getAirDate();
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            try {
                Date newDate = input.parse(date);
                assert newDate != null;
                holder.episodeRelease.setText(output.format(newDate));
            } catch (ParseException ignored) {

            }
        }
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    class SeasonViewHolder extends RecyclerView.ViewHolder {
        ImageView episodePoster;
        TextView episodeName;
        TextView episodeDescription;
        TextView episodeNumber;
        TextView episodeRelease;

        SeasonViewHolder(@NonNull View itemView) {
            super(itemView);
            episodePoster = itemView.findViewById(R.id.episode_poster);
            episodeName = itemView.findViewById(R.id.episode_title);
            episodeDescription = itemView.findViewById(R.id.episode_overview);
            episodeNumber = itemView.findViewById(R.id.episode_number);
            episodeRelease = itemView.findViewById(R.id.episode_release);
        }
    }

}
