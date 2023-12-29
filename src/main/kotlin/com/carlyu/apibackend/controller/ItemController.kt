package com.carlyu.apibackend.controller


import com.carlyu.apibackend.dto.CreateItemDto
import com.carlyu.apibackend.dto.ItemDto
import com.carlyu.apibackend.dto.toDto
import com.carlyu.apibackend.entity.Item
import com.carlyu.apibackend.exceptions.ApiResponseStatusException
import com.carlyu.apibackend.service.ItemService
import com.carlyu.apibackend.utils.toUser
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemService: ItemService,
) {

    @GetMapping
    fun getItems(authentication: Authentication): List<ItemDto> {
        val authUser = authentication.toUser()

        return itemService.findByUser(authUser).map { item -> item.toDto() }
    }

    @PostMapping
    fun createItem(authentication: Authentication, @RequestBody payload: CreateItemDto) {
        val authUser = authentication.toUser()

        if (itemService.existsByNameAndUser(payload.name, authUser)) {
            throw ApiResponseStatusException(409, "Item name already exists")
        }

        val item = Item(
            user = authUser,
            name = payload.name,
            count = payload.count,
            note = payload.note,
        )

        itemService.save(item)
    }
}