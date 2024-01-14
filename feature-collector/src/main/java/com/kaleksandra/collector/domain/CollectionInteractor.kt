package com.kaleksandra.collector.domain

import com.kaleksandra.coredata.network.Completable
import com.kaleksandra.coredata.network.Effect
import com.kaleksandra.coredata.network.models.CardCollectionDto
import com.kaleksandra.coredata.network.models.CollectionDto
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.coredata.network.models.GroupResponse
import com.kaleksandra.coredata.network.models.MemberResponse
import com.kaleksandra.coredata.network.repository.CollectionRepository
import java.io.File
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

    override suspend fun createCollection(collection: CollectionDto): Effect<Long> {
        return repository.createCollection(collection)
    }

    override suspend fun getGroups(query: String): Effect<List<GroupResponse>> {
        return repository.getGroups(query)
    }

    override suspend fun getAllMembersGroup(id: Long): Effect<List<MemberResponse>> {
        return repository.getAllMembersGroup(id)
    }

    override suspend fun uploadImage(file: File, id: Long): Effect<Long> {
        return repository.uploadImage(file, id)
    }

    override suspend fun setCardCollection(cardId: Long, collectionId: Long): Effect<Unit> {
        return repository.setCardCollection(CardCollectionDto(cardId, collectionId))
    }
}

interface CollectionInteractor {
    suspend fun getCollection(id: Long): Effect<CollectionResponse>
    suspend fun getAllCollection(): Effect<List<CollectionResponse>>
    suspend fun addCardInCollection(cardId: Long): Effect<Completable>
    suspend fun createCollection(collection: CollectionDto): Effect<Long>
    suspend fun getGroups(query: String): Effect<List<GroupResponse>>
    suspend fun getAllMembersGroup(id: Long): Effect<List<MemberResponse>>
    suspend fun uploadImage(file: File, id: Long): Effect<Long>
    suspend fun setCardCollection(cardId: Long, collectionId: Long): Effect<Unit>
}

