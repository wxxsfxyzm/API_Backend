package com.carlyu.apibackend.controller

import com.carlyu.apibackend.dto.LoginDto
import com.carlyu.apibackend.dto.LoginResponseDto
import com.carlyu.apibackend.dto.RegisterDto
import com.carlyu.apibackend.entity.User
import com.carlyu.apibackend.exceptions.ApiException
import com.carlyu.apibackend.service.HashService
import com.carlyu.apibackend.service.TokenService
import com.carlyu.apibackend.service.UserService
import com.carlyu.apibackend.vo.ResultVO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
) {

    @PostMapping("/login")
    fun login(@RequestBody payload: LoginDto): LoginResponseDto {
        val user = userService.findByUsername(payload.username) ?: throw ApiException(400, "Login failed")

        if (!hashService.checkBcrypt(payload.password, user.password)) {
            throw ApiException(400, "Login failed")
        }

        return LoginResponseDto(
            msg = "Login successfully",
            token = tokenService.createToken(user),
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody payload: RegisterDto): LoginResponseDto {
        if (userService.existsByUsername(payload.username)) {
            throw ApiException(400, "Name already exists")
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

    @GetMapping("/logout_success")
    fun logoutSuccess(): ResultVO {
        val str = "Logout successfully"
        val code = 200
        return ResultVO(
            msg = str,
            code = code,
        )
    }
}