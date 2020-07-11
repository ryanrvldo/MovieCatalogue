package com.dicoding.moviecataloguerv.data.source.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "query_search")
public class Search {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String query;

    @Ignore
    public Search(String query) {
        this.query = query;
    }
}
