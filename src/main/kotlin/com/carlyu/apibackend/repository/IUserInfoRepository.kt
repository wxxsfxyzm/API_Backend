package com.carlyu.apibackend.repository

import com.carlyu.apibackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserInfoRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User?

    fun findByUsernameAndPassword(username: String, password: String): User?

}