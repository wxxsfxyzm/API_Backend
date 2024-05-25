package com.carlyu.apibackend.utils

import com.carlyu.apibackend.constant.BaseConstant
import com.carlyu.apibackend.enums.APIType
import com.carlyu.apibackend.enums.APIType.GOOGLE_GEMINI_API

object UrlUtil {
    fun buildRequestHost(
        urlType: APIType,
    ): String = when (urlType) {
        GOOGLE_GEMINI_API -> BaseConstant.getGoogleGeminiApiHost()
        else -> throw IllegalArgumentException("Invalid API Type")
    }

    fun buildRequestPath(
        urlType: APIType,
        modelName: String,
        modeName: String
    ): String = when (urlType) {
        GOOGLE_GEMINI_API -> BaseConstant.getGoogleGeminiApiPathUrl(modelName, modeName)
        else -> throw IllegalArgumentException("Invalid API Type")
    }

}