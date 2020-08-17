package com.ryanrvldo.moviecatalogue.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.ryanrvldo.moviecatalogue.data.source.model.ContentRating;

import java.util.List;

import lombok.Data;

@Data
public class ContentRatingResponse {
    @Expose
    private List<ContentRating> results;
}