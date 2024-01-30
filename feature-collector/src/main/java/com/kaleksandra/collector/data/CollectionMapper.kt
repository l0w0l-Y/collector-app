package com.kaleksandra.collector.data

import com.kaleksandra.collector.domain.models.CollectionModel
import com.kaleksandra.collector.domain.models.Group
import com.kaleksandra.collector.domain.models.Member
import com.kaleksandra.coredata.network.models.CollectionRequest
import com.kaleksandra.coredata.network.models.CollectionResponse
import com.kaleksandra.coredata.network.models.GroupResponse
import com.kaleksandra.coredata.network.models.MemberResponse
import com.kaleksandra.collector.domain.models.CollectionItem as CollectionItemDomain
import com.kaleksandra.coredata.network.models.CollectionItemResponse as CollectionItemData

fun CollectionResponse.toDomain(): CollectionModel {
    return CollectionModel(
        id = id,
        title = title,
        list = this.list.map { it.toDomain() },
        cards = cards,
        cardsInCollection = cardsInCollection
    )
}

fun List<CollectionModel>.updateCardInCollection(cardId: Long): List<CollectionModel> {
    return this.map { collection ->
        val collectionCardIds = collection.list.map { it.id }
        if (cardId in collectionCardIds) {
            val updatedList = collection.list.map { card ->
                card.copy(inCollection = if (card.id == cardId) !card.inCollection else card.inCollection)
            }
            val cardInCollection = updatedList.count { it.inCollection }
            collection.copy(
                list = updatedList,
                cardsInCollection = cardInCollection
            )
        } else {
            collection
        }
    }
}

fun toData(collection: String, groupId: Long): CollectionRequest {
    return CollectionRequest(collection, groupId)
}

fun GroupResponse.toDomain(): Group {
    return Group(
        id = id,
        name = name,
        description = description,
    )
}

fun MemberResponse.toDomain(): Member {
    return Member(
        id = id,
        nameEn = nameEn,
        nameRu = nameRu,
        nameKor = nameKor,
        nickname = nickname,
        link = link,
    )
}

private fun CollectionItemData.toDomain(): CollectionItemDomain {
    return CollectionItemDomain(
        id = id,
        memberName = memberName,
        description = description,
        link = link,
        inCollection = inCollection,
    )
}
