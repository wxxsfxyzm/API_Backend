package com.carlyu.apibackend.dto

import com.carlyu.apibackend.entity.GoogleAIUserSafetySettings
import com.nimbusds.jose.shaded.gson.GsonBuilder
import com.nimbusds.jose.shaded.gson.annotations.Expose
import java.io.Serializable


data class Part(@Expose val text: String?)

data class Contents(@Expose val parts: List<Part>)

data class GenerationConfig(
    @Expose val temperature: Double = 1.00,
    @Expose val candidate_count: Int?
)

data class SafetySetting(
    @Expose val category: String,
    @Expose val threshold: String
)

data class SystemInstruction(@Expose val parts: Part)

data class GoogleApiRequestDTO(
    @Expose val contents: List<Contents>?,
    @Expose val safety_settings: List<SafetySetting>?, // 使用下划线命名法
    @Expose val generation_config: GenerationConfig?,  // 使用下划线命名法
    @Expose val system_instruction: SystemInstruction? // 使用下划线命名法
) : Serializable {
    companion object {
        fun Any.toJsonString(): String {
            val gson = GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create()
            return gson.toJson(this)
        }

        fun fromString(json: String): GoogleApiRequestDTO {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.fromJson(json, GoogleApiRequestDTO::class.java)
        }

        fun createBody(
            contentsText: String,
            safetySettings: List<GoogleAIUserSafetySettings>,
            temperature: Double,
            candidateCount: Int?,
            systemInstructionText: String?
        ): GoogleApiRequestDTO {
            val contents = Contents(listOf(Part(contentsText)))
            val generationConfig = GenerationConfig(temperature, candidateCount)
            val systemInstruction = SystemInstruction(Part(systemInstructionText))
            // Convert List<GoogleAIUserSafetySettings> to List<SafetySetting>
            val convertedSafetySettings = safetySettings.map { SafetySetting(it.category, it.threshold) }
            return GoogleApiRequestDTO(listOf(contents), convertedSafetySettings, generationConfig, systemInstruction)
        }
    }
}