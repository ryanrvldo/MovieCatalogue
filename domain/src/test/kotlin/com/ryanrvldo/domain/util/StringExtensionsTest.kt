package com.ryanrvldo.domain.util

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class StringExtensionsTest {

    // GIVEN
    private val nonNullString: String = "String"
    private val nullString: String? = null

    @Test
    @DisplayName("given null string when convert string, should return non-null default string")
    internal fun testConvertNullString() {
        // WHEN
        val actual = nullString.orDefault()

        // THEN
        assertThat(actual).isNotNull()
        assertThat(actual).isNotEmpty()
        assertThat(actual).isEqualTo(DEFAULT_CONTENT_STRING)
    }

    @Test
    @DisplayName("given non-null string with value when convert string, should return non-null and value not changed")
    internal fun testConvertNonNullString() {
        // WHEN
        val actual = nonNullString.orDefault()

        // THEN
        assertThat(actual).isNotNull()
        assertThat(actual).isNotEmpty()
        assertThat(actual).isEqualTo(nonNullString)
    }

}