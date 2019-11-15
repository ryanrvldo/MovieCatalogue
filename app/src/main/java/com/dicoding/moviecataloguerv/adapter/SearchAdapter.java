package com.dicoding.moviecataloguerv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Search> searchList;
    private OnItemClicked onItemClicked;

    public SearchAdapter(ArrayList<Search> searchList, OnItemClicked onItemClicked) {
        this.searchList = searchList;
        this.onItemClicked = onItemClicked;
    }

    public void refillSearch(List<Search> searchList) {
        this.searchList.clear();
        this.searchList.addAll(searchList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bindSearch(searchList.get(position));
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public interface OnItemClicked {
        void onDeleteClick(Search search);

        void onSearchClick(Search search);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuery;
        ImageView imgClear;
        Search search;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuery = itemView.findViewById(R.id.tv_query);
            imgClear = itemView.findViewById(R.id.clear_recent_item);

            imgClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onDeleteClick(search);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onSearchClick(search);
                }
            });
        }

        private void bindSearch(Search search) {
            this.search = search;

            tvQuery.setText(search.getQuery());
        }
    }
}
