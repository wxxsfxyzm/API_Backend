package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import com.carlyu.apibackend.enums.APIType
import com.carlyu.apibackend.enums.APIType.GENSIN
import com.carlyu.apibackend.enums.APIType.GOOGLE_GEMINI_API
import com.carlyu.apibackend.service.APIRemoteAccess
import com.carlyu.apibackend.service.APIRemoteResponseHandler
import com.carlyu.apibackend.service.UserService
import com.carlyu.apibackend.utils.UrlUtil
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
        requestModelName: String,
        requestModeName: String,
        text: String
    ): String = jacksonObjectMapper()
        .readTree(
            apiRemoteAccess.visitRemoteAPI(
                UrlUtil.buildRequestHost(GOOGLE_GEMINI_API),
                UrlUtil.buildRequestPath(GOOGLE_GEMINI_API, requestModelName, requestModeName),
                authentication.toUser().googleApiKey,
                GoogleApiRequestDTO.createBody(
                    text,
                    authentication.toUser().safetySettings,
                    authentication.toUser().generationConfig!!.temperature,
                    authentication.toUser().generationConfig!!.candidateCount,
                    authentication.toUser().googleSystemInstruction
                )
            )
        )
        .findValuesAsText("text")[0].toString()

    override fun handleString(
        authentication: Authentication,
        requestApi: APIType,
        text: String
    ): String {
        return when (requestApi) {
            GOOGLE_GEMINI_API ->
                handleGoogleChatInOneTimeGenerationMode(authentication, text)

            GENSIN -> {
                "gensin"
            }
        }
    }

    private fun handleGoogleChatInOneTimeGenerationMode(
        authentication: Authentication,
        text: String
    ): String {
        val user = authentication.toUser()
        var existingPrompt = ""
        if (!user.googleSessionIsActive) { // if user has no active session
            user.googleSessionIsActive = true // set user session to active
        } else { // if user has active session
            existingPrompt = user.googleTextHistory
        }
        existingPrompt += text
        userService.save(user.apply { googleTextHistory = existingPrompt })
        return existingPrompt
    }
}