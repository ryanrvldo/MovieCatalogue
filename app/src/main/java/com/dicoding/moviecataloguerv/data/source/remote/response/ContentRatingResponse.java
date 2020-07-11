package com.dicoding.moviecataloguerv.data.source.remote.response;

import com.dicoding.moviecataloguerv.data.source.model.ContentRating;
import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.Data;

@Data
public class ContentRatingResponse {
    @Expose
    private List<ContentRating> results;
}