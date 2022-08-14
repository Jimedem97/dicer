package de.jimedem.dicer.data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import de.jimedem.dicer.dto.ConfigDto

@JsonClass(generateAdapter = true)
data class AppConfig(
    val device: String,
    val configDto: ConfigDto
){
    fun toJson(): String {
        return jsonAdapter.toJson(this)
    }

    companion object {
        private val jsonAdapter by lazy {
            Moshi.Builder().build().adapter(AppConfig::class.java)
        }

        fun fromJson(json: String): AppConfig? {
            return jsonAdapter.fromJson(json)
        }
    }
}
