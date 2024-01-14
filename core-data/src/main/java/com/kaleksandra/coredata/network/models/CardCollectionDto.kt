package com.kaleksandra.coredata.network.models

import kotlinx.serialization.Serializable

@Serializable
data class CardCollectionDto(
    val cardId: Long,
    val collectionId: Long,
)