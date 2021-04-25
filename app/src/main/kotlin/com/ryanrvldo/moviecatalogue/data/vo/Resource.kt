package com.ryanrvldo.moviecatalogue.data.vo

import com.ryanrvldo.moviecatalogue.data.vo.Status.*

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(SUCCESS, data, null)

        fun <T> error(msg: String, data: T? = null): Resource<T> = Resource(ERROR, data, msg)

        fun <T> loading(data: T? = null): Resource<T> = Resource(LOADING, data, null)
    }
}