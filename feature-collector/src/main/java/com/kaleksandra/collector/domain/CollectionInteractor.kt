package com.kaleksandra.collector.domain

import com.kaleksandra.collector.data.toData
import com.kaleksandra.collector.data.toDomain
import com.kaleksandra.collector.domain.models.CollectionModel
import com.kaleksandra.collector.domain.models.Group
import com.kaleksandra.collector.domain.models.Member
import com.kaleksandra.coredata.network.Completable
import com.kaleksandra.coredata.network.Effect
import com.kaleksandra.coredata.network.map
import com.kaleksandra.coredata.network.models.CardCollectionRequest
import com.kaleksandra.coredata.network.repository.CollectionRepository
import java.io.File
import javax.inject.Inject

class CollectionInteractorImpl @Inject constructor(
    private val repository: CollectionRepository
) : CollectionInteractor {
    override suspend fun getCollection(id: Long): Effect<CollectionModel> {
        return repository.getCollection(id).map { it.toDomain() }
    }

    override suspend fun getAllCollection(): Effect<List<CollectionModel>> {
        return repository.getAllCollection().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addCardInCollection(cardId: Long): Effect<Completable> {
        return repository.addCardInCollection(cardId)
    }

    override suspend fun createCollection(collection: String, groupId: Long): Effect<Long> {
        return repository.createCollection(toData(collection, groupId))
    }

    override suspend fun getGroups(query: String): Effect<List<Group>> {
        return repository.getGroups(query).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getAllMembersGroup(id: Long): Effect<List<Member>> {
        return repository.getAllMembersGroup(id).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun uploadImage(file: File, id: Long): Effect<Long> {
        return repository.uploadImage(file, id)
    }

    override suspend fun setCardCollection(cardId: Long, collectionId: Long): Effect<Unit> {
        return repository.setCardCollection(CardCollectionRequest(cardId, collectionId))
    }
}

interface CollectionInteractor {
    suspend fun getCollection(id: Long): Effect<CollectionModel>
    suspend fun getAllCollection(): Effect<List<CollectionModel>>
    suspend fun addCardInCollection(cardId: Long): Effect<Completable>
    suspend fun createCollection(collection: String, groupId: Long): Effect<Long>
    suspend fun getGroups(query: String): Effect<List<Group>>
    suspend fun getAllMembersGroup(id: Long): Effect<List<Member>>
    suspend fun uploadImage(file: File, id: Long): Effect<Long>
    suspend fun setCardCollection(cardId: Long, collectionId: Long): Effect<Unit>
}