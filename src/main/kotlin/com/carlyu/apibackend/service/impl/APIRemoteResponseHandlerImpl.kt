package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import com.carlyu.apibackend.enums.url
import com.carlyu.apibackend.service.APIRemoteAccess
import com.carlyu.apibackend.service.APIRemoteResponseHandler
import com.carlyu.apibackend.service.UserService
import com.carlyu.apibackend.utils.toUser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class APIRemoteResponseHandlerImpl(
    private val apiRemoteAccess: APIRemoteAccess,
    private val userService: UserService
) : APIRemoteResponseHandler {
    override fun handleResponse(
        authentication: Authentication,
        text: String
    ): String = jacksonObjectMapper()
        .readTree(
            apiRemoteAccess.visitRemoteAPI(
                url.GOOGLE_GEMINI_API.url,
                authentication.toUser().googleApiKey,
                GoogleApiRequestDTO.createBody(text)
            )
        )
        .findValuesAsText("text")[0].toString()

    override fun handleString(
        authentication: Authentication,
        requestApi: String,
        text: String
    ): String {
        return when (requestApi) {
            "googleApi" ->
                handleGoogleApiPrompt(authentication, text)

            "gensin" -> {
                "gensin"
            }

            else -> {
                "error"
            }
        }
    }

    fun handleGoogleApiPrompt(
        authentication: Authentication,
        text: String
    ): String {
        val user = authentication.toUser()
        var existingPrompt = ""
        if (!user.googleSessionIsActive) { // if user has no active session
            user.googleSessionIsActive = true // set user session to active
        } else { // if user has active session
            existingPrompt = user.googlePrompt
        }
        existingPrompt += text
        userService.save(user)
        return existingPrompt
    }
}