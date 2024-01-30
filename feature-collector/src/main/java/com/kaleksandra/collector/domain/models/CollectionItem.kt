package com.kaleksandra.collector.domain.models

data class CollectionItem(
    val id: Long,
    val memberName: String,
    val description: String?,
    val link: String,
    val inCollection: Boolean,
)