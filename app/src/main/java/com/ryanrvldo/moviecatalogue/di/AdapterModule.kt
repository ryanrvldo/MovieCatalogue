package com.ryanrvldo.moviecatalogue.di

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.*
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.ui.viewmodel.MovieDetailsViewModel
import com.ryanrvldo.moviecatalogue.ui.viewmodel.TvShowDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {

    @Provides
    fun provideOnMovieItemClick(fragment: Fragment) =
        object : OnItemClicked<Movie> {
            override fun onItemClicked(movie: Movie) {
                val bundle = Bundle()
                bundle.putInt(MovieDetailsViewModel.MOVIE_ID_KEY, movie.id)
                fragment.findNavController().navigate(R.id.movieDetailActivity, bundle)
            }
        }

    @Provides
    fun provideDifferMovieCallback() =
        object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }

    @Provides
    fun provideMoviesHorizontalAdapter(
        onItemClicked: OnItemClicked<Movie>,
        itemCallback: DiffUtil.ItemCallback<Movie>
    ) = MoviesHorizontalAdapter(onItemClicked, itemCallback)

    @Provides
    fun provideMoviesVerticalAdapter(
        onItemClicked: OnItemClicked<Movie>,
        differCallback: DiffUtil.ItemCallback<Movie>
    ) = MoviesVerticalAdapter(onItemClicked, differCallback)

    @Provides
    fun provideOnTvShowItemClick(fragment: Fragment) =
        object : OnItemClicked<TvShow> {
            override fun onItemClicked(tvShow: TvShow) {
                val bundle = Bundle()
                bundle.putInt(TvShowDetailsViewModel.TV_SHOW_ID_KEY, tvShow.id)
                fragment.findNavController().navigate(R.id.tvShowDetailActivity, bundle)
            }
        }

    @Provides
    fun provideDifferTvShowCallback() =
        object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }

    @Provides
    fun provideTvShowsHorizontalAdapter(
        onItemClicked: OnItemClicked<TvShow>,
        itemCallback: DiffUtil.ItemCallback<TvShow>
    ) = TvShowsHorizontalAdapter(onItemClicked, itemCallback)

    @Provides
    fun provideTvShowsVerticalAdapter(
        onItemClicked: OnItemClicked<TvShow>,
        itemCallback: DiffUtil.ItemCallback<TvShow>
    ) = TvShowsVerticalAdapter(onItemClicked, itemCallback)

    @Provides
    fun provideViewPagerAdapter(fragment: Fragment) = ViewPagerAdapter(fragment)

}