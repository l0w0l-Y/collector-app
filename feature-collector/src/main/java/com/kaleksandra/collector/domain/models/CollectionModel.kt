package com.kaleksandra.collector.domain.models

data class CollectionModel(
    val id: Long,
    val title: String,
    val list: List<CollectionItem>,
    val cards: Int,
    val cardsInCollection: Int,
)