package com.ryanrvldo.data.extension

import com.google.common.truth.Truth.assertThat

fun <T> Result<T>.assertError(fakeException: Throwable) {
    assertThat(isFailure).isTrue()
    assertThat(exceptionOrNull()).isInstanceOf(fakeException::class.java)
    assertThat(exceptionOrNull()).isEqualTo(fakeException)
}