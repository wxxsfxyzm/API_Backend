package com.carlyu.apibackend.dao

import com.carlyu.apibackend.entity.TokenBlacklistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp


interface TokenBlacklistRepository : JpaRepository<TokenBlacklistEntity, Long> {

    fun existsByTokenValue(tokenValue: String): Boolean

    fun deleteByExpirationTimeBefore(expirationTime: Timestamp)

    // 自定义查询方法，用于删除过期的 Token 记录
    @Transactional
    @Modifying
    @Query("DELETE FROM TokenBlacklistEntity t WHERE t.expirationTime < :expirationTime")
    fun deleteExpiredTokens(expirationTime: Timestamp)

    // 自定义查询方法，用于归档过期的 Token 记录到另一个表
    // 这里的目标表需要根据实际情况创建
    //fun archiveExpiredTokens(expirationTime: Instant)
}