package com.carlyu.apibackend.controller

import com.carlyu.apibackend.dto.LoginDto
import com.carlyu.apibackend.dto.LoginResponseDto
import com.carlyu.apibackend.dto.RegisterDto
import com.carlyu.apibackend.entity.User
import com.carlyu.apibackend.exceptions.ApiResponseStatusException
import com.carlyu.apibackend.service.HashService
import com.carlyu.apibackend.service.TokenBlacklistCleanupService
import com.carlyu.apibackend.service.TokenService
import com.carlyu.apibackend.service.UserService
import com.carlyu.apibackend.utils.toUser
import com.carlyu.apibackend.vo.ResultVO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
    private val tokenBlacklistCleanupService: TokenBlacklistCleanupService
) {

    @PostMapping("/login")
    fun login(@RequestBody payload: LoginDto): LoginResponseDto {
        val user =
            userService.findByUsername(payload.username) ?: throw ApiResponseStatusException(400, "User not exist")

        if (!hashService.checkBcrypt(payload.password, user.password)) {
            throw ApiResponseStatusException(400, "Wrong password")
        }

        return LoginResponseDto(
            msg = "1",//authentication.details.toString(),
            token = tokenService.createToken(user),
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody payload: RegisterDto): LoginResponseDto {
        if (userService.existsByUsername(payload.username)) {
            throw ApiResponseStatusException(400, "Name already exists")
        }

        val user = User(
            username = payload.username,
            password = hashService.hashBcrypt(payload.password),
        )

        val savedUser = userService.save(user)

        return LoginResponseDto(
            msg = "Register successfully",
            token = tokenService.createToken(savedUser!!),
        )
    }

    @PostMapping("/forgetPassword")
    fun forgetPassword(@RequestBody payload: RegisterDto): ResultVO {

        if (!userService.existsByUsername(payload.username)) {
            throw ApiResponseStatusException(400, "User not exist")
        }

        val user = userService.findByUsername(payload.username)!!
        user.password = hashService.hashBcrypt(payload.password)
        userService.save(user)

        return ResultVO(
            msg = "Reset password successfully",
            code = 200,
        )
    }

    @GetMapping("/logout_success")
    fun logoutSuccess(): ResultVO {

        val str = "Logout successfully"
        val code = 200
        return ResultVO(
            msg = str,
            code = code,
        )
    }

    @PostMapping("/userinfo/update")
    fun updateUserInfo(
        authentication: Authentication,
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestParam("username") username: String,
        @RequestParam("password") password: String,
        @RequestParam("googleApiKey") googleApiKey: String,
    ): ResultVO {
        val user = authentication.toUser()
        if (user.username != username ||
            user.password != hashService.hashBcrypt(password)
        ) {
            log.info("User ${user.username} has updated username or password")
            user.username = username
            user.password = hashService.hashBcrypt(password)
            user.googleApiKey = googleApiKey
            userService.save(user)
            performLogout(request, response)
        } else {
            log.info("User ${user.username} has updated googleApiKey")
            user.googleApiKey = googleApiKey
            userService.save(user)
        }

        return ResultVO(
            msg = "Update successfully",
            code = 200,
        )
    }

    fun performLogout(request: HttpServletRequest, response: HttpServletResponse) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        log.info("User ${authentication?.name} has logged out")
        tokenBlacklistCleanupService.setTokenExpired(authentication!!)
        SecurityContextLogoutHandler().logout(request, response, authentication)
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}