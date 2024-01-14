package com.kaleksandra.coredata.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GroupResponse(
    val id: Long,
    val name: String,
    val description: String?,
)