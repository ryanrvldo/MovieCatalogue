package com.ryanrvldo.moviecatalogue.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryanrvldo.moviecatalogue.data.source.model.Cast;

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
