package com.carlyu.apibackend.vo

data class ApiResponseVO(
    val apiResponse: String
) {
    
    override fun toString(): String {
        return "ApiResponseVO(apiResponse='$apiResponse')"
    }
}