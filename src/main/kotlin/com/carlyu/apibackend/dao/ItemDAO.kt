package com.carlyu.apibackend.dao

import com.carlyu.apibackend.entity.Item
import com.carlyu.apibackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ItemDAO : JpaRepository<Item, Long> {
    fun findByUser(user: User): List<Item>
    fun findByNameAndUser(name: String, user: User): Item?

    fun existsByNameAndUser(name: String, user: User): Boolean
}