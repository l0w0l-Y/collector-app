package com.kaleksandra.coredata.network.models

import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    val id: Long,
    val nameEn: String?,
    val nameRu: String?,
    val nameKor: String?,
    val nickname: String?,
    val link: String?,
)