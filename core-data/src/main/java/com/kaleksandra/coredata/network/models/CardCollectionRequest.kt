package com.kaleksandra.coredata.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardCollectionRequest(
    @SerialName("cardId")
    val cardId: Long,
    @SerialName("collectionId")
    val collectionId: Long,
)