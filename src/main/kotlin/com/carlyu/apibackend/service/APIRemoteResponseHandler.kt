package com.carlyu.apibackend.service

import org.springframework.security.core.Authentication

interface APIRemoteResponseHandler {
    fun handleResponse(
        authentication: Authentication,
        text: String
    ): String

    fun handleString(
        authentication: Authentication,
        requestApi: String,
        text: String
    ): String
}