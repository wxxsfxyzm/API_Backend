package com.carlyu.apibackend.controller

import com.carlyu.apibackend.dto.LoginDto
import com.carlyu.apibackend.dto.LoginResponseDto
import com.carlyu.apibackend.dto.RegisterDto
import com.carlyu.apibackend.entity.GoogleAIUserConfig
import com.carlyu.apibackend.entity.GoogleAIUserSafetySettings
import com.carlyu.apibackend.entity.User
import com.carlyu.apibackend.enums.GoogleHarmCategory
import com.carlyu.apibackend.enums.GoogleHarmThreshold
import com.carlyu.apibackend.exceptions.ApiResponseStatusException
import com.carlyu.apibackend.service.HashService
import com.carlyu.apibackend.service.TokenService
import com.carlyu.apibackend.service.UserService
import com.carlyu.apibackend.utils.ResultVOUtil
import com.carlyu.apibackend.utils.toUser
import com.carlyu.apibackend.vo.ResultVO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
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
    fun register(@RequestBody payload: RegisterDto): ResultVO {
        if (userService.existsByUsername(payload.username)) {
            return ResultVOUtil.fail(400, "User already exists")
        }

        val user = User(
            username = payload.username,
            password = hashService.hashBcrypt(payload.password),
        )
        log.info("User ${user.username} has registered")
        val defaultConfig = GoogleAIUserConfig() // Create a default GoogleAIUserConfig instance


        val savedUser = userService.saveUserWithConfigAndInstruction(
            user,
            defaultConfig,
            "",
            setSafetyConfig(null, null, null, null)
        )
        log.info("User ${savedUser?.username} has registered")
        return ResultVOUtil.success(
            LoginResponseDto(
                msg = "Register successfully",
                token = tokenService.createToken(savedUser!!),
            )
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

    @PostMapping("/set_google_system_instruction")
    fun setGoogleSystemInstruction(
        authentication: Authentication,
        @RequestParam("instruction") instruction: String,
    ): ResultVO {
        val user = authentication.toUser()
        user.googleSystemInstruction = instruction
        userService.save(user)
        return ResultVO(
            msg = "Set instruction successfully",
            code = 200,
            data = instruction
        )
    }

    @PostMapping("/set_google_generation_config")
    fun setGoogleGenerationConfig(
        authentication: Authentication,
        @RequestParam("temperature") temp: Double,
        @RequestParam("candidateCount") count: Int,
        @RequestParam("topP") topp: Double,
        @RequestParam("topK") topk: Int,
    ): ResultVO {
        val user = authentication.toUser()
        log.info("User ${user.username} has updated generation config")
        // 更新用户的 generationConfig
        val updatedConfig = GoogleAIUserConfig(
            id = user.generationConfig?.id ?: throw RuntimeException("Config not found"),
            temperature = temp,
            candidateCount = count,
            topP = topp,
            topK = topk,
        )

        return if (userService.updateUserConfig(user.id!!, updatedConfig))
            ResultVO(
                msg = "Set generation config successfully",
                code = 200,
                data = updatedConfig
            )
        else
            ResultVO(
                msg = "Set generation config failed",
                code = 400,
            )
    }


    @PostMapping("/set_safety_settings")
    fun setSafetySettings(
        authentication: Authentication,
        @RequestParam("t1") t1: GoogleHarmThreshold,
        @RequestParam("t2") t2: GoogleHarmThreshold,
        @RequestParam("t3") t3: GoogleHarmThreshold,
        @RequestParam("t4") t4: GoogleHarmThreshold,
    ): ResultVO {
        val user = authentication.toUser()
        userService.updateUserSafetySettings(user.id!!, setSafetyConfig(t1, t2, t3, t4))
        return ResultVO(
            msg = "Set safety settings successfully",
            code = 200,
            data = user.safetySettings
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

    @GetMapping("/logout_success")
    fun logoutSuccess(): ResultVO {

        val str = "Logout successfully"
        val code = 200
        return ResultVO(
            msg = str,
            code = code,
        )
    }

    private fun performLogout(request: HttpServletRequest, response: HttpServletResponse) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        log.info("User ${authentication?.name} has logged out")
        SecurityContextLogoutHandler().logout(request, response, authentication)
    }

    private fun setSafetyConfig(
        t1: GoogleHarmThreshold?,
        t2: GoogleHarmThreshold?,
        t3: GoogleHarmThreshold?,
        t4: GoogleHarmThreshold?
    ) =
        listOf(
            GoogleAIUserSafetySettings(
                category = GoogleHarmCategory.HARM_CATEGORY_HARASSMENT.toString(),
                threshold = t1?.toString() ?: GoogleHarmThreshold.BLOCK_LOW_AND_ABOVE.toString(),
            ),
            GoogleAIUserSafetySettings(
                category = GoogleHarmCategory.HARM_CATEGORY_HATE_SPEECH.toString(),
                threshold = t2?.toString() ?: GoogleHarmThreshold.BLOCK_LOW_AND_ABOVE.toString(),
            ),
            GoogleAIUserSafetySettings(
                category = GoogleHarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT.toString(),
                threshold = t3?.toString() ?: GoogleHarmThreshold.BLOCK_LOW_AND_ABOVE.toString(),
            ),
            GoogleAIUserSafetySettings(
                category = GoogleHarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT.toString(),
                threshold = t4?.toString() ?: GoogleHarmThreshold.BLOCK_LOW_AND_ABOVE.toString(),
            )
        )

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}