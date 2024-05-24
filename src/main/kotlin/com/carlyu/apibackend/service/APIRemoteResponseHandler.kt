package com.carlyu.apibackend.service

import com.carlyu.apibackend.enums.APIType
import org.springframework.security.core.Authentication

interface APIRemoteResponseHandler {
    fun handleResponse(
        authentication: Authentication,
        requestModelName: String,
        requestModeName: String,
        text: String
    ): String

    fun handleString(
        authentication: Authentication,
        requestApi: APIType,
        text: String
    ): String
}