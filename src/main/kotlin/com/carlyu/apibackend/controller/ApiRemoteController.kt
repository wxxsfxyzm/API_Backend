package com.carlyu.apibackend.controller

import com.carlyu.apibackend.dto.GoogleApiRequestDTO.Companion.createData
import com.carlyu.apibackend.enums.url.GOOGLE_GEMINI_API
import com.carlyu.apibackend.service.APIRemoteAccess
import com.carlyu.apibackend.service.APIRemoteResponseHandler
import com.carlyu.apibackend.utils.toUser
import com.carlyu.apibackend.vo.ApiResponseVO
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/remoteAccess")
class ApiRemoteController(
    private val apiRemoteAccess: APIRemoteAccess,
    private val apiRemoteResponseHandler: APIRemoteResponseHandler
) {
    @PostMapping("/googleApi")
    fun googleApi(
        authentication: Authentication,
        @RequestParam("text") text: String
    ): ApiResponseVO {
        val authUser = authentication.toUser()
        val googleApiKey = authUser.googleApiKey

        log.info("GOOGLE_API_KEY: $googleApiKey")
        log.info("GOOGLE_GEMINI_API.url: ${GOOGLE_GEMINI_API.url}")

        //apiRemoteAccess.visitRemoteAPI(GOOGLE_GEMINI_API.url, googleApiKey)
        val requestBody = createData(text)

        return ApiResponseVO(
            apiResponse = apiRemoteResponseHandler.handleResponse(
                ApiResponseVO(
                    apiResponse = apiRemoteAccess.visitRemoteAPI(GOOGLE_GEMINI_API.url, googleApiKey, requestBody)
                )
            )
        )

    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}