package com.carlyu.apibackend.controller

import com.carlyu.apibackend.service.APIRemoteResponseHandler
import com.carlyu.apibackend.service.UserService
import com.carlyu.apibackend.utils.toUser
import com.carlyu.apibackend.vo.ApiResponseVO
import com.carlyu.apibackend.vo.ResultVO
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/remoteAccess")
class ApiRemoteController(
    private val userService: UserService,
    private val apiRemoteResponseHandler: APIRemoteResponseHandler
) {
    @PostMapping("/googleApi")
    fun googleApi(
        authentication: Authentication,
        @RequestParam("text") text: String
    ): ApiResponseVO = ApiResponseVO(
        apiResponse = apiRemoteResponseHandler
            .handleResponse(
                authentication,
                apiRemoteResponseHandler.handleString(
                    authentication,
                    "googleApi",
                    text
                )
            )
    )

    @GetMapping("/googleApi/terminate")
    fun terminateGoogleSession(
        authentication: Authentication
    ): ResultVO {
        val user = authentication.toUser()
        log.info(authentication.toString())
        if (user.googleSessionIsActive) {
            user.googleSessionIsActive = false
            user.googlePrompt = ""
            userService.save(user)
            return ResultVO(
                msg = "Session terminated",
                code = 200
            )
        } else {
            // TODO make an exception class handling this
            log.info("User ${user.username} has no active session")
            return ResultVO(
                msg = "User ${user.username} has no active session",
                code = 400
            )
        }
    }

    @PostMapping("/gensin")
    fun gensin(
        authentication: Authentication,
        @RequestParam("text") text: String
    ): ApiResponseVO = ApiResponseVO(
        apiResponse = apiRemoteResponseHandler
            .handleResponse(authentication, "介绍一下原神")
    )

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}