package com.kaleksandra.collector.domain.models

data class Member(
    val id: Long,
    val nameEn: String?,
    val nameRu: String?,
    val nameKor: String?,
    val nickname: String?,
    val link: String?,
)