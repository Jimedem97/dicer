package de.jimedem.dicer.dto

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class ConfigDto(
    val initialTickDurationMs: Int,
    val percentTickIncrease: Int,
    val lastTickMs: Int,
    val targets: List<List<Int>>,
    val animation: String
) {

    fun toJson(): String {
        return jsonAdapter.toJson(this)
    }

    companion object {
        private val jsonAdapter by lazy {
            Moshi.Builder().build().adapter(ConfigDto::class.java)
        }

        fun fromJson(json: String): ConfigDto? {
            return jsonAdapter.fromJson(json)
        }
    }
}
