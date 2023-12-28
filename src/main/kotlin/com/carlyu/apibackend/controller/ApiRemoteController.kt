package com.carlyu.apibackend.controller

import com.carlyu.apibackend.service.APIRemoteResponseHandler
import com.carlyu.apibackend.vo.ApiResponseVO
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/remoteAccess")
class ApiRemoteController(
    private val apiRemoteResponseHandler: APIRemoteResponseHandler
) {
    @PostMapping("/googleApi")
    fun googleApi(
        authentication: Authentication,
        @RequestParam("text") text: String
    ): ApiResponseVO = ApiResponseVO(
        apiResponse = apiRemoteResponseHandler
            .handleResponse(authentication, text)
    )
}