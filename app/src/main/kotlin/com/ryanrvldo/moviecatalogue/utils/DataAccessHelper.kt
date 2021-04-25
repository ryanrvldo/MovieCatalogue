package com.ryanrvldo.moviecatalogue.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.data.vo.Status.ERROR
import com.ryanrvldo.moviecatalogue.data.vo.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <T> performGetOperation(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            emit(responseStatus)
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }