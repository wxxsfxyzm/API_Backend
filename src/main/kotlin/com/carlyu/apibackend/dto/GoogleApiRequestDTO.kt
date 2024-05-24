package com.carlyu.apibackend.dto

import com.carlyu.apibackend.entity.GoogleAIUserSafetySettings
import com.nimbusds.jose.shaded.gson.GsonBuilder
import java.io.Serializable

data class Part(val text: String?)

data class Contents(val parts: List<Part>)

data class GenerationConfig(
    val temperature: Double = 1.00,
    val candidateCount: Int?
)

data class SystemInstruction(val parts: Part)

data class GoogleApiRequestDTO(
    val contents: Contents?,
    val safetySettings: List<GoogleAIUserSafetySettings>?,
    val generationConfig: GenerationConfig?,
    val systemInstruction: SystemInstruction?
) : Serializable {
    companion object {
        fun Any.toJsonString(): String {
            val gson = GsonBuilder().setPrettyPrinting().create()
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
            return GoogleApiRequestDTO(contents, safetySettings, generationConfig, systemInstruction)
        }
    }
}