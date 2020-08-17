package com.ryanrvldo.moviecatalogue.data.source.model;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class Genre {
    @Expose
    private int id;

    @Expose
    private String name;
}

