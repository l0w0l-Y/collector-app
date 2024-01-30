package com.kaleksandra.coredata.network.api

import com.kaleksandra.coredata.network.models.CardCollectionRequest
import com.kaleksandra.coredata.network.models.CollectionRequest
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.coredata.network.models.GroupResponse
import com.kaleksandra.coredata.network.models.MemberResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionApi {
    @GET("collection/{id}")
    suspend fun getCollection(@Path("id") id: Long): Response<CollectionResponse>

    @GET("collection/all")
    suspend fun getAllCollection(): Response<List<CollectionResponse>>

    @POST("collection/card/{cardId}")
    suspend fun addCardInCollection(@Path("cardId") cardId: Long): Response<Unit>

    @POST("collection")
    suspend fun createCollection(@Body collection: CollectionRequest): Response<Long>

    @GET("collection/groups")
    suspend fun getAllGroups(@Query("query") query: String): Response<List<GroupResponse>>

    @GET("collection/groups/{id}")
    suspend fun getAllMembersGroup(@Path("id") id: Long): Response<List<MemberResponse>>

    @Multipart
    @POST("collection/image/{id}")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Path("id") id: Long
    ): Response<Long>

    @POST("collection/card")
    suspend fun setCardCollection(@Body cardCollection: CardCollectionRequest) : Response<Unit>
}