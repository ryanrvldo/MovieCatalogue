package com.dicoding.moviecataloguerv.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "query_search")
public class Search {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String query;

    public Search(int id, String query) {
        this.id = id;
        this.query = query;
    }

    @Ignore
    public Search(String query) {
        this.query = query;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
