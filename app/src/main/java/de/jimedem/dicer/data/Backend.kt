package de.jimedem.dicer.data

import de.jimedem.dicer.dto.ConfigDto
import de.jimedem.dicer.service.DicerWebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

sealed class Backend(
    val name: String,
    val hostname: String,
    var reachable: MutableStateFlow<Boolean> = MutableStateFlow(false)
) {

    val webService by lazy { webService().create(DicerWebService::class.java) }

    private fun webService(): Retrofit {
        return Retrofit.Builder().baseUrl(hostname)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    suspend fun observeAlive() {
        try {
            reachable.value = webService.isAlive().isSuccessful
        } catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun start(): Boolean{
        return webService.start().isSuccessful
    }

    suspend fun stop(): Boolean{
        return webService.stop().isSuccessful
    }

    suspend fun configure(dto: ConfigDto): Boolean{
        return webService.configure(dto).isSuccessful
    }

    object RaspberryPi : Backend("Raspberry Pi", "http://joni-media")

    //    object Laptop : BaseUrl("Laptop", "joni-laptop-alt")
    object Laptop : Backend("Laptop", "http://10.0.2.2:8080")
}
