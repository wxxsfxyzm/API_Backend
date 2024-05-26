package com.carlyu.apibackend.service

import com.carlyu.apibackend.entity.GoogleAIUserConfig
import com.carlyu.apibackend.entity.GoogleAIUserSafetySettings
import com.carlyu.apibackend.entity.User
import java.util.*

interface UserService {
    fun findById(id: UUID): User?

    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean
    fun save(user: User): User?

    fun saveUserWithConfigAndInstruction(
        user: User,
        generationConfig: GoogleAIUserConfig,
        systemInstruction: String,
        safetySettings: List<GoogleAIUserSafetySettings>
    ): User?


    fun updateUserSafetySettings(userId: UUID, updatedSettings: List<GoogleAIUserSafetySettings>): Boolean

    fun updateUserConfig(userId: UUID, updatedConfig: GoogleAIUserConfig): Boolean
}