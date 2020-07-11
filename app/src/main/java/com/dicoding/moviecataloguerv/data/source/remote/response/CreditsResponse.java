package com.dicoding.moviecataloguerv.data.source.remote.response;

import com.dicoding.moviecataloguerv.data.source.model.Cast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class CreditsResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("cast")
    @Expose
    private List<Cast> cast;
}
