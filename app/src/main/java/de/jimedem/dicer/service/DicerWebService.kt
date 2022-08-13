package de.jimedem.dicer.service

import de.jimedem.dicer.dto.ConfigDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DicerWebService {

    @GET("/isAlive")
    suspend fun isAlive(): Response<Void>

    @POST("/dicer/start")
    suspend fun start(): Response<Void>

    @POST("/dicer/stop")
    suspend fun stop(): Response<Void>

    @POST("/dicer/config")
    suspend fun configure(@Body dto: ConfigDto): Response<Void>
}