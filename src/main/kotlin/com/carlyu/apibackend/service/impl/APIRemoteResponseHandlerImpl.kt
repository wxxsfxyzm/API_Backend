package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import com.carlyu.apibackend.enums.url
import com.carlyu.apibackend.service.APIRemoteAccess
import com.carlyu.apibackend.service.APIRemoteResponseHandler
import com.carlyu.apibackend.utils.toUser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class APIRemoteResponseHandlerImpl(
    private val apiRemoteAccess: APIRemoteAccess
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

}