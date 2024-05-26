package com.carlyu.apibackend.service

import com.carlyu.apibackend.entity.Item
import com.carlyu.apibackend.entity.User
import com.carlyu.apibackend.repository.ItemDAO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemDAO: ItemDAO,
) {
    fun findById(id: Long): Item? {
        return itemDAO.findByIdOrNull(id)
    }

    fun findByUser(user: User): List<Item> {
        return itemDAO.findByUser(user)
    }

    fun findByNameAndUser(name: String, user: User): Item? {
        return itemDAO.findByNameAndUser(name, user)
    }

    fun existsByNameAndUser(name: String, user: User): Boolean {
        return itemDAO.existsByNameAndUser(name, user)
    }

    fun save(item: Item): Item {
        return itemDAO.save(item)
    }

    fun delete(item: Item) {
        return itemDAO.delete(item)
    }
}