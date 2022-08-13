package de.jimedem.dicer.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigDto(
    val initialTickDurationMs: Double,
    val percentTickIncrease: Int,
    val lastTickMs: Int,
    val targets: List<List<Int>>,
    val animation: String
)
