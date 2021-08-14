package com.ryanrvldo.data.network.response

object ResponsePath {
    const val NOT_FOUND = "api-response/not_found.json"
    const val UNAUTHORIZED = "api-response/unauthorized.json"

    object GetMovies {
        const val POPULAR_MOVIES_PAGE_1 = "api-response/response_popular_movie_page1.json"
        const val POPULAR_MOVIES_PAGE_2 = "api-response/response_popular_movie_page2.json"
        const val MOVIE_DETAILS_WITH_APPEND_846214 =
            "api-response/response_movie_details_with_append_846214.json"
    }

    object GetTvShows {
        const val POPULAR_TV_SHOWS_PAGE_1 = "api-response/response_popular_tv_show_page1.json"
        const val POPULAR_TV_SHOWS_PAGE_2 = "api-response/response_popular_tv_show_page1.json"
        const val TV_SHOW_DETAILS_WITH_APPEND_95281 =
            "api-response/response_tv_show_details_with_append_95281.json"
    }
}