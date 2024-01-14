package com.kaleksandra.coredata.network.repository

import com.kaleksandra.coredata.network.Completable
import com.kaleksandra.coredata.network.Effect
import com.kaleksandra.coredata.network.api.CollectionApi
import com.kaleksandra.coredata.network.call
import com.kaleksandra.coredata.network.di.IoDispatcher
import com.kaleksandra.coredata.network.models.CardCollectionDto
import com.kaleksandra.coredata.network.models.CollectionDto
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.coredata.network.models.GroupResponse
import com.kaleksandra.coredata.network.models.MemberResponse
import com.kaleksandra.coredata.network.toCompletable
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

interface CollectionRepository {
    suspend fun getCollection(id: Long): Effect<CollectionResponse>
    suspend fun getAllCollection(): Effect<List<CollectionResponse>>
    suspend fun addCardInCollection(cardId: Long): Effect<Completable>
    suspend fun createCollection(collection: CollectionDto): Effect<Long>
    suspend fun getGroups(query: String): Effect<List<GroupResponse>>
    suspend fun getAllMembersGroup(id: Long): Effect<List<MemberResponse>>
    suspend fun uploadImage(file: File, id: Long): Effect<Long>
    suspend fun setCardCollection(cardCollectionDto: CardCollectionDto): Effect<Unit>
}

class CollectionRepositoryImpl @Inject constructor(
    val api: CollectionApi,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : CollectionRepository {
    override suspend fun getCollection(id: Long): Effect<CollectionResponse> {
        return call(dispatcher) { api.getCollection(id) }
    }

    override suspend fun getAllCollection(): Effect<List<CollectionResponse>> {
        return call(dispatcher) { api.getAllCollection() }
    }

    override suspend fun addCardInCollection(cardId: Long): Effect<Completable> {
        return call(dispatcher) { api.addCardInCollection(cardId) }.toCompletable()
    }

    override suspend fun createCollection(collection: CollectionDto): Effect<Long> {
        return call(dispatcher) { api.createCollection(collection) }
    }

    override suspend fun getGroups(query: String): Effect<List<GroupResponse>> {
        return call(dispatcher) { api.getAllGroups(query) }
    }

    override suspend fun getAllMembersGroup(id: Long): Effect<List<MemberResponse>> {
        return call(dispatcher) { api.getAllMembersGroup(id) }
    }

    override suspend fun uploadImage(file: File, id: Long): Effect<Long> {
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            file.name,
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        )
        return call(dispatcher) { api.uploadImage(multipartBody, id) }
    }

    override suspend fun setCardCollection(cardCollectionDto: CardCollectionDto): Effect<Unit> {
        return call(dispatcher) { api.setCardCollection(cardCollectionDto) }
    }
}