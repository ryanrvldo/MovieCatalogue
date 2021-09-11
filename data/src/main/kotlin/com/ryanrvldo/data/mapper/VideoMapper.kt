package com.ryanrvldo.data.mapper

import com.ryanrvldo.data.network.response.commons.VideoResponse
import com.ryanrvldo.domain.model.commons.Video
import javax.inject.Inject

class VideoMapper @Inject constructor() {

    fun mapVideoResponseToDomain(response: VideoResponse) = Video(
        id = response.id,
        key = response.key,
        title = response.name,
        site = response.site,
        type = response.type,
        isOfficial = response.official,
        publishedDate = response.publishedAt
    )

}