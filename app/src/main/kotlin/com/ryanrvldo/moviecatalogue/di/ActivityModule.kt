package com.ryanrvldo.moviecatalogue.di

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.DiffUtil
import com.ryanrvldo.moviecatalogue.adapter.CastAdapter
import com.ryanrvldo.moviecatalogue.adapter.EpisodeAdapter
import com.ryanrvldo.moviecatalogue.adapter.OnItemClicked
import com.ryanrvldo.moviecatalogue.adapter.SimilarAdapter
import com.ryanrvldo.moviecatalogue.data.model.Cast
import com.ryanrvldo.moviecatalogue.data.model.Episode
import com.ryanrvldo.moviecatalogue.data.model.Similar
import com.ryanrvldo.moviecatalogue.ui.detail.MovieDetailsActivity
import com.ryanrvldo.moviecatalogue.ui.detail.TvShowDetailsActivity
import com.ryanrvldo.moviecatalogue.ui.viewmodel.MovieDetailsViewModel.Companion.MOVIE_ID_KEY
import com.ryanrvldo.moviecatalogue.ui.viewmodel.TvShowDetailsViewModel.Companion.TV_SHOW_ID_KEY
import com.ryanrvldo.moviecatalogue.utils.Constants.MOVIE_TYPE
import com.ryanrvldo.moviecatalogue.utils.Constants.TV_TYPE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideDifferCastCallback() =
        object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }

    @Provides
    fun provideCastAdapter(itemCallback: DiffUtil.ItemCallback<Cast>) =
        CastAdapter(itemCallback)

    @MovieQualifier
    @Provides
    fun provideMovieSimilarOnItemClick(activity: Activity): OnItemClicked<Similar> =
        OnItemClicked { similar ->
            Intent(activity, MovieDetailsActivity::class.java).also {
                it.putExtra(MOVIE_ID_KEY, similar.id)
                activity.startActivity(it)
            }
        }


    @MovieQualifier
    @Provides
    fun provideMovieSimilarAdapter(@MovieQualifier callback: OnItemClicked<Similar>) =
        SimilarAdapter(mutableListOf(), MOVIE_TYPE, callback)

    @TvShowQualifier
    @Provides
    fun provideTvShowSimilarOnItemClick(activity: Activity) =
        OnItemClicked<Similar> { similar ->
            Intent(activity, TvShowDetailsActivity::class.java).also {
                it.putExtra(TV_SHOW_ID_KEY, similar.id)
                activity.startActivity(it)
            }
        }

    @TvShowQualifier
    @Provides
    fun provideTvShowSimilarAdapter(@TvShowQualifier callback: OnItemClicked<Similar>) =
        SimilarAdapter(mutableListOf(), TV_TYPE, callback)


    @Provides
    fun provideEpisodeDifferCallback() =
        object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }

    @Provides
    fun provideEpisodeAdapter(itemCallback: DiffUtil.ItemCallback<Episode>) =
        EpisodeAdapter(itemCallback)

}
