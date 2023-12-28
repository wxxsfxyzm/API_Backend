package com.carlyu.apibackend.service

import com.carlyu.apibackend.entity.User

interface TokenService {
    fun createToken(user: User): String
    fun parseToken(token: String): User?
}