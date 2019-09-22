package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.Genre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenresResponse {
    @SerializedName("genres")
    @Expose
    private ArrayList<Genre> genres;

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
