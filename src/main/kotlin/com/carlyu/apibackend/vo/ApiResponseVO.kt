package com.carlyu.apibackend.vo

import java.io.Serializable

data class ApiResponseVO(
    val apiResponse: String
) : Serializable {

    override fun toString(): String {
        return "ApiResponseVO(apiResponse='$apiResponse')"
    }
}