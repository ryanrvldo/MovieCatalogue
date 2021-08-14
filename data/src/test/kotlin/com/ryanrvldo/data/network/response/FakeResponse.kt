package com.ryanrvldo.data.network.response

import com.ryanrvldo.data.util.getResourceAsText

object FakeResponse {
    val NOT_FOUND_ERROR by lazy { getResourceAsText(ResponsePath.NOT_FOUND) }
    val UNAUTHORIZED_ERROR by lazy { getResourceAsText(ResponsePath.UNAUTHORIZED) }

    val POPULAR_MOVIES_PAGE_1 by lazy { getResourceAsText(ResponsePath.GetMovies.POPULAR_MOVIES_PAGE_1) }
    val POPULAR_MOVIES_PAGE_2 by lazy { getResourceAsText(ResponsePath.GetMovies.POPULAR_MOVIES_PAGE_2) }
    val MOVIE_DETAILS_WITH_APPEND_846214 by lazy { getResourceAsText(ResponsePath.GetMovies.MOVIE_DETAILS_WITH_APPEND_846214) }

    val POPULAR_TV_SHOW_PAGE_1 by lazy { getResourceAsText(ResponsePath.GetTvShows.POPULAR_TV_SHOWS_PAGE_1) }
    val POPULAR_TV_SHOW_PAGE_2 by lazy { getResourceAsText(ResponsePath.GetTvShows.POPULAR_TV_SHOWS_PAGE_2) }
    val TV_SHOW_DETAILS_WITH_APPEND_95281 by lazy { getResourceAsText(ResponsePath.GetTvShows.TV_SHOW_DETAILS_WITH_APPEND_95281) }
}