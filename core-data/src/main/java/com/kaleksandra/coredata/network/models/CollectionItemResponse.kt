package com.kaleksandra.coredata.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionItemResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("memberName")
    val memberName: String,
    @SerialName("description")
    val description: String?,
    @SerialName("link")
    val link: String,
    @SerialName("inCollection")
    val inCollection: Boolean,
)