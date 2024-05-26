package com.carlyu.apibackend.repository

import com.carlyu.apibackend.entity.GoogleAIUserSafetySettings
import com.carlyu.apibackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IGoogleAIUserSafetySettingsRepository : JpaRepository<GoogleAIUserSafetySettings, Long> {
    fun deleteByUser(user: User)

    fun deleteByUserId(userId: UUID)

    fun findByCategoryAndUserId(category: String, userId: UUID): GoogleAIUserSafetySettings?


}