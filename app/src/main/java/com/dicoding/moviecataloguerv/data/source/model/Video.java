package com.dicoding.moviecataloguerv.data.source.model;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class Video {
    @Expose
    private String key;

    @Expose
    private String name;
}