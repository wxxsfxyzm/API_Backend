package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.entity.GoogleAIUserConfig
import com.carlyu.apibackend.entity.GoogleAIUserSafetySettings
import com.carlyu.apibackend.entity.User
import com.carlyu.apibackend.repository.IGoogleAIUserConfigRepository
import com.carlyu.apibackend.repository.IGoogleAIUserSafetySettingsRepository
import com.carlyu.apibackend.repository.IUserInfoRepository
import com.carlyu.apibackend.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(
    val iUserInfoRepository: IUserInfoRepository,
    val iGoogleAIUserConfigRepository: IGoogleAIUserConfigRepository,
    val iGoogleAIUserSafetySettingsRepository: IGoogleAIUserSafetySettingsRepository
) : UserService {

    override fun findById(id: UUID): User? = iUserInfoRepository.findById(id).orElse(null)


    override fun findByUsername(username: String): User? = iUserInfoRepository.findByUsername(username)


    override fun existsByUsername(username: String): Boolean = iUserInfoRepository.findByUsername(username) != null


    override fun save(user: User): User? = iUserInfoRepository.save(user)


    override fun saveUserWithConfigAndInstruction(
        user: User,
        generationConfig: GoogleAIUserConfig,
        systemInstruction: String,
        safetySettings: List<GoogleAIUserSafetySettings>
    ): User? {
        user.generationConfig = generationConfig
        user.googleSystemInstruction = systemInstruction
        user.safetySettings = safetySettings.map { it.user = user; it }.toMutableList()
        return iUserInfoRepository.save(user)
    }

    @Transactional
    override fun updateUserConfig(userId: UUID, updatedConfig: GoogleAIUserConfig): Boolean {
        val user = iUserInfoRepository.findById(userId).orElseThrow { RuntimeException("User not found") }

        println("Updating user config with id: $userId")

        val existingConfig = iGoogleAIUserConfigRepository.findById(updatedConfig.id)
            .orElseThrow { RuntimeException("Config not found") }

        println("Existing config: $existingConfig")

        // Update fields of the existing config
        existingConfig.temperature = updatedConfig.temperature
        existingConfig.candidateCount = updatedConfig.candidateCount
        existingConfig.topP = updatedConfig.topP
        existingConfig.topK = updatedConfig.topK

        // Set the existing config back to the user
        user.generationConfig = existingConfig

        println("Updated user: $user")

        iUserInfoRepository.save(user)

        return true
    }

    @Transactional
    override fun updateUserSafetySettings(userId: UUID, updatedSettings: List<GoogleAIUserSafetySettings>): Boolean {
        val user = iUserInfoRepository.findById(userId).orElseThrow { RuntimeException("User not found") }

        updatedSettings.forEach { updatedSetting ->
            val existingSetting =
                iGoogleAIUserSafetySettingsRepository.findByCategoryAndUserId(updatedSetting.category, userId)
                    ?: throw RuntimeException("Safety setting for category ${updatedSetting.category} not found")

            existingSetting.threshold = updatedSetting.threshold
            iGoogleAIUserSafetySettingsRepository.save(existingSetting)
        }
        return true
    }

}
