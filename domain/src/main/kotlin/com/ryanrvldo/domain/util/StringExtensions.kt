package com.ryanrvldo.domain.util

const val DEFAULT_CONTENT_STRING = "-"

fun String?.orDefault(): String {
    return this?.let {
        if (this.isBlank() || this.isEmpty()) DEFAULT_CONTENT_STRING
        this
    } ?: DEFAULT_CONTENT_STRING
}