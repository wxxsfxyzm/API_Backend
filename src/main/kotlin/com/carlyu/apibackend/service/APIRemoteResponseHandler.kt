package com.carlyu.apibackend.service

import org.springframework.security.core.Authentication

interface APIRemoteResponseHandler {
    fun handleResponse(
        authentication: Authentication,
        requestModelName: String,
        text: String
    ): String

    fun handleString(
        authentication: Authentication,
        requestApi: String,
        text: String
    ): String
}