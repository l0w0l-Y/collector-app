package com.kaleksandra.coredata.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("nameEn")
    val nameEn: String?,
    @SerialName("nameRu")
    val nameRu: String?,
    @SerialName("nameKor")
    val nameKor: String?,
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("link")
    val link: String?,
)