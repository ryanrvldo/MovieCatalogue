package com.ryanrvldo.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.bumptech.glide.Glide
import com.ryanrvldo.moviecatalogue.BuildConfig
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.data.model.Movie
import java.util.concurrent.ExecutionException

class StackRemoteViewsFactory(
    private val mContext: Context
) : RemoteViewsFactory {

    private var movieFavorite: List<Movie> = emptyList()

    override fun onCreate() {
        try {
            movieFavorite = emptyList()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onDataSetChanged() {
        try {
            movieFavorite = emptyList()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {}
    override fun getCount(): Int {
        return movieFavorite.size
    }

    override fun getViewAt(position: Int): RemoteViews? {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.widget_item)
        return if (movieFavorite.isNotEmpty()) {
            val movie =
                movieFavorite[position]
            try {
                val bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.TMDB_IMAGE_342 + movie.posterPath)
                    .submit(512, 512)
                    .get()
                remoteViews.setImageViewBitmap(R.id.imageWidget, bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val extras = Bundle()
            extras.putString(FavoriteMovieWidget.EXTRA_ITEM, movie.title)
            val fillInIntent = Intent()
            fillInIntent.putExtras(extras)
            remoteViews.setOnClickFillInIntent(R.id.imageWidget, fillInIntent)
            Log.d(TAG, "Data is more than 0")
            remoteViews
        } else {
            Log.d(TAG, "Data is null")
            null
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    companion object {
        private const val TAG = "Widget"
    }

}