package com.carlyu.apibackend.controller

import com.carlyu.apibackend.enums.APIType.GOOGLE_GEMINI_API
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
    @PostMapping("/runGoogleGenerativeAI")
    fun googleApi(
        authentication: Authentication,
        @RequestParam("text") text: String,
        @RequestParam("modelName") requestModelName: String,
        @RequestParam("modeName") requestModeName: String,
    ): ApiResponseVO = ApiResponseVO(
        apiResponse = apiRemoteResponseHandler
            .handleResponse(
                authentication,
                requestModelName,
                requestModeName,
                apiRemoteResponseHandler.handleString(
                    authentication,
                    GOOGLE_GEMINI_API,
                    text
                )
            )
    )

    @GetMapping("/googleSession/terminate")
    fun terminateGoogleSession(
        authentication: Authentication
    ): ResultVO {
        val user = authentication.toUser()
        log.info(authentication.toString())
        if (user.googleSessionIsActive) {
            user.googleSessionIsActive = false
            user.googleTextHistory = ""
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

    // TODO handle system instruction

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}

