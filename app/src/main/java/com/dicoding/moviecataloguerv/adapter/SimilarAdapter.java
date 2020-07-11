package com.dicoding.moviecataloguerv.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.data.source.model.Similar;
import com.dicoding.moviecataloguerv.databinding.ItemSimilarBinding;

import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder> {

    private List<Similar> similarList;
    private String type;
    private OnItemClicked onItemClicked;

    public SimilarAdapter(List<Similar> similarList, String type, OnItemClicked onItemClicked) {
        this.similarList = similarList;
        this.type = type;
        this.onItemClicked = onItemClicked;
    }

    public void setSimilarList(List<Similar> similarList) {
        this.similarList.clear();
        this.similarList.addAll(similarList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarViewHolder(ItemSimilarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarViewHolder holder, int position) {
        Similar similar = similarList.get(position);

        if (type.equalsIgnoreCase("movie")) {
            holder.binding.titleSimilar.setText(similar.getTitle());
        } else if (type.equalsIgnoreCase("tv")) {
            holder.binding.titleSimilar.setText(similar.getName());
        }
        holder.binding.ratingSimilar.setText(String.valueOf(similar.getRating()));
        Glide.with(holder.itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + similar.getPosterPath())
                .placeholder(R.drawable.ic_undraw_images)
                .error(R.drawable.ic_undraw_404)
                .centerCrop()
                .into(holder.binding.posterSimilar);
        holder.binding.getRoot().setOnClickListener(view -> onItemClicked.onItemClick(similar.getId()));
    }

    @Override
    public int getItemCount() {
        return similarList.size();
    }

    public interface OnItemClicked {
        void onItemClick(int id);
    }

    public static class SimilarViewHolder extends RecyclerView.ViewHolder {
        private ItemSimilarBinding binding;

        public SimilarViewHolder(@NonNull ItemSimilarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
