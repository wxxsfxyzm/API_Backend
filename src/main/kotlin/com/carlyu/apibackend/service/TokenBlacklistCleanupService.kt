package com.carlyu.apibackend.service

import com.carlyu.apibackend.dao.TokenBlacklistRepository
import com.carlyu.apibackend.entity.TokenBlacklistEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
class TokenBlacklistCleanupService(
    private val tokenBlacklistRepository: TokenBlacklistRepository
) {

    fun setTokenExpired(authentication: Authentication) {
        val tokenBlacklistEntity = TokenBlacklistEntity(
            tokenValue = authentication.credentials.toString(),
            expirationTime = Timestamp.from(Instant.ofEpochMilli(authentication.details as Long))
        )
        tokenBlacklistRepository.save(tokenBlacklistEntity)

    }

    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨执行
    fun cleanupExpiredTokens() {
        val now = Timestamp.from(Instant.now())
        tokenBlacklistRepository.deleteExpiredTokens(now)
    }

}