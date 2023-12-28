package com.carlyu.apibackend.service

interface HashService {
    /**
     * checks whether the string matches the hash
     * @param input the string to be checked
     * @param hash the hashed string
     * @return true if hash(input) == hash, otherwise false
     */
    fun checkBcrypt(input: String, hash: String): Boolean

    /**
     * hashes the string
     * @param input the string to be hashed
     * @return the hashed string
     */
    fun hashBcrypt(input: String): String
}