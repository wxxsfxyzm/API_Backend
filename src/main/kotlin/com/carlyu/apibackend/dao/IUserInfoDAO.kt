package com.carlyu.apibackend.dao

import com.carlyu.apibackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface IUserInfoDAO : JpaRepository<User, String> {
    fun findByUsername(username: String): User?

    fun findByUsernameAndPassword(username: String, password: String): User?

    fun findById(id: Long): User?

}