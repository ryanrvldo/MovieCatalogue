package com.ryanrvldo.moviecatalogue.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryanrvldo.moviecatalogue.data.source.model.Genre;

import java.util.ArrayList;

import lombok.Data;

@Data
public class GenresResponse {
    @SerializedName("genres")
    @Expose
    private ArrayList<Genre> genres;
}
