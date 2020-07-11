package com.dicoding.moviecataloguerv.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.dicoding.moviecataloguerv.data.source.model.Genre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class StringUtils {
    public static String getGenres(@NonNull List<Genre> genres) {
        List<String> currentGenres = new ArrayList<>();
        for (Genre genre : genres) {
            currentGenres.add(genre.getName());
        }
        return TextUtils.join(", ", currentGenres);
    }

    public static String getReleaseDate(@NonNull String releaseDate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat output = new SimpleDateFormat("yyyy", Locale.US);
        try {
            Date newDate = input.parse(releaseDate);
            return output.format(Objects.requireNonNull(newDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getRuntime(int duration) {
        int hour = duration / 60;
        int minute = duration % 60;
        return String.format(Locale.US, "%dh %02dmin", hour, minute);
    }
}
