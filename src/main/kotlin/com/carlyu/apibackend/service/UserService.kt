package com.carlyu.apibackend.service

import com.carlyu.apibackend.entity.User

interface UserService {
    fun findById(id: Long): User?

    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean
    fun save(user: User): User?
}