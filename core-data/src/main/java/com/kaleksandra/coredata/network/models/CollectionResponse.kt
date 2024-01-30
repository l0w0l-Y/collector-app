package com.kaleksandra.coredata.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("list")
    val list: List<CollectionItemResponse>,
    @SerialName("cards")
    val cards: Int,
    @SerialName("cardsInCollection")
    val cardsInCollection: Int,
)