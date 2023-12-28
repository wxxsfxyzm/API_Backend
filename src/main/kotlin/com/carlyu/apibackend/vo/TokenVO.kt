package com.carlyu.apibackend.vo

import java.io.Serializable

data class TokenVO(
    val msg: String,
    val state: Boolean,
    val token: String
) : Serializable {

}