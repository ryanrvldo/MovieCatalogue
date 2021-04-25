package com.ryanrvldo.moviecatalogue.utils

import android.text.TextUtils
import com.ryanrvldo.moviecatalogue.data.model.Genre
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    fun getGenres(genres: List<Genre>): String {
        val currentGenres = mutableListOf<String>()
        for (genre in genres) {
            currentGenres.add(genre.name)
        }
        return TextUtils.join(", ", currentGenres)
    }

    fun getReleaseDate(releaseDate: String): String? {
        val input = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val output = SimpleDateFormat("yyyy", Locale.US)
        val newDate = try {
            input.parse(releaseDate)
        } catch (e: ParseException) {
            null
        }
        newDate?.let {
            return output.format(newDate)
        }
        return null
    }

    fun getRuntime(duration: Int): String {
        val hour = duration / 60
        val minute = duration % 60
        return String.format(Locale.US, "%dh %02d min", hour, minute)
    }
}