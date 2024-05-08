package com.carlyu.apibackend.entity

import jakarta.persistence.*
import java.sql.Timestamp


@Entity
@Table(name = "token_blacklist")
data class TokenBlacklistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val tokenValue: String,

    val expirationTime: Timestamp
)