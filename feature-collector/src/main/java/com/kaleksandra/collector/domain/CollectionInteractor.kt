package com.kaleksandra.collector.domain

import com.kaleksandra.coredata.network.Completable
import com.kaleksandra.coredata.network.Effect
import com.kaleksandra.coredata.network.models.CollectionDto
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.coredata.network.repository.CollectionRepository
import javax.inject.Inject

class CollectionInteractorImpl @Inject constructor(
    val repository: CollectionRepository
) : CollectionInteractor {
    override suspend fun getCollection(id: Long): Effect<CollectionResponse> {
        return repository.getCollection(id)
    }

    override suspend fun getAllCollection(): Effect<List<CollectionResponse>> {
        return repository.getAllCollection()
    }

    override suspend fun addCardInCollection(cardId: Long): Effect<Completable> {
        return repository.addCardInCollection(cardId)
    }

    override suspend fun addCollection(collection: CollectionDto): Effect<Long> {
        return repository.addCollection(collection)
    }
}

interface CollectionInteractor {
    suspend fun getCollection(id: Long): Effect<CollectionResponse>
    suspend fun getAllCollection(): Effect<List<CollectionResponse>>
    suspend fun addCardInCollection(cardId: Long): Effect<Completable>
    suspend fun addCollection(collection: CollectionDto): Effect<Long>
}

