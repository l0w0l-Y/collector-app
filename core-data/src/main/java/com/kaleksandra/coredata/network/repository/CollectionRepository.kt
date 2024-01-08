package com.kaleksandra.coredata.network.repository

import com.kaleksandra.coredata.network.Completable
import com.kaleksandra.coredata.network.Effect
import com.kaleksandra.coredata.network.api.CollectionApi
import com.kaleksandra.coredata.network.call
import com.kaleksandra.coredata.network.di.IoDispatcher
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.coredata.network.toCompletable
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface CollectionRepository {
    suspend fun getCollection(id: Long): Effect<CollectionResponse>
    suspend fun getAllCollection(): Effect<List<CollectionResponse>>
    suspend fun addCardInCollection(cardId: Long): Effect<Completable>
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
}