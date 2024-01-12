package com.kaleksandra.coredata.network.api

import com.kaleksandra.coredata.network.models.CollectionDto
import com.kaleksandra.coredata.network.models.CollectionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CollectionApi {
    @GET("collection/{id}")
    suspend fun getCollection(@Path("id") id: Long): Response<CollectionResponse>

    @GET("collection/all")
    suspend fun getAllCollection(): Response<List<CollectionResponse>>

    @POST("collection/card/{cardId}")
    suspend fun addCardInCollection(@Path("cardId") cardId: Long): Response<Unit>

    @POST("/")
    suspend fun addCollection(@Body collection: CollectionDto): Response<Long>
}