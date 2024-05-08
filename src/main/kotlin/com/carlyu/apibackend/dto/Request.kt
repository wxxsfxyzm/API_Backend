package com.carlyu.apibackend.dto

data class LoginDto(
    val username: String,
    val password: String,
)

data class RegisterDto(
    val message: String?,
    val username: String,
    val password: String,
)

data class CreateItemDto(
    val name: String,
    val count: Int,
    val note: String?,
)

data class UpdateItemDto(
    val id: Long,
    val name: String,
    val count: Int,
    val note: String?,
)
