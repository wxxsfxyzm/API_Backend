package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.service.HashService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class HashServiceImpl : HashService {
    override fun checkBcrypt(input: String, hash: String): Boolean = BCrypt.checkpw(input, hash)


    override fun hashBcrypt(input: String): String = BCrypt.hashpw(input, BCrypt.gensalt(10))

}