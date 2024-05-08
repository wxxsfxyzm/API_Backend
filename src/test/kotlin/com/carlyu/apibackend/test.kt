package com.carlyu.apibackend

import java.security.SecureRandom
import java.util.*

fun generateRandomKey(): String {
    // 生成一个 256 位的随机字节数组
    val randomBytes = ByteArray(256 / 8)
    SecureRandom().nextBytes(randomBytes)

    // 将字节数组转换为 Base64 编码的字符串
    return Base64.getEncoder().encodeToString(randomBytes)
}

fun main() {
    val randomKey = generateRandomKey()
    println("Random Key: $randomKey")
}