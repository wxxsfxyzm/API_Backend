package com.carlyu.apibackend.dto

import com.carlyu.apibackend.entity.Item

/**
 * This file contains all mapping extension methods for DTOs.
 * In this simple example, there is only [Item] and [ItemDto].
 */
fun Item.toDto(): ItemDto {
    return ItemDto(id, name, count, note)
}