package com.ryanrvldo.moviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ryanrvldo.moviecatalogue.BuildConfig;
import com.ryanrvldo.moviecatalogue.R;
import com.ryanrvldo.moviecatalogue.data.source.model.Cast;
import com.ryanrvldo.moviecatalogue.databinding.ItemCastBinding;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private List<Cast> castList;

    public CastAdapter(List<Cast> casts) {
        this.castList = casts;
    }

    public void setCastList(List<Cast> casts) {
        this.castList.clear();
        this.castList.addAll(casts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(ItemCastBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Cast cast = castList.get(position);
        holder.binding.castName.setText(cast.getName());
        holder.binding.castCharacter.setText(cast.getCharacter());
        Glide.with(holder.itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + cast.getProfilePath())
                .error(R.drawable.ic_undraw_avatar)
                .placeholder(R.drawable.ic_undraw_images)
                .override(120, 120)
                .into(holder.binding.castPhoto);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder {
        private ItemCastBinding binding;

        public CastViewHolder(@NonNull ItemCastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
