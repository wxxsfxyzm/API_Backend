package com.carlyu.apibackend.dto


data class LoginResponseDto(
    val msg: String,
    val token: String,
)

data class ItemDto(
    val id: Long,
    val name: String,
    val count: Int,
    val note: String?,
)