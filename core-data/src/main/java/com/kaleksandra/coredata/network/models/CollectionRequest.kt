package com.kaleksandra.coredata.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionRequest(
    @SerialName("title")
    val title: String,
    @SerialName("groupId")
    val groupId: Long? = null,
)