package com.carlyu.apibackend.dto

import com.nimbusds.jose.shaded.gson.GsonBuilder
import java.io.Serializable


data class Part(val text: String)

data class Contents(val parts: List<Part>)

data class GoogleApiRequestDTO(val contents: Contents) : Serializable {

    companion object {
        // 扩展函数，用于将数据类转换为 JSON 字符串
        fun Any.toJsonString(): String {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.toJson(this)
        }

        // 示例数据的创建函数
        fun createBody(text: String): GoogleApiRequestDTO {
            val part1 = Part(text)
            val contents1 = Contents(listOf(part1))
            return GoogleApiRequestDTO(contents1)
        }
    }
}