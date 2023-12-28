package com.carlyu.apibackend.service

import com.carlyu.apibackend.vo.ApiResponseVO

interface ResponseHandler {
    fun handleResponse(responseVO: ApiResponseVO): String
}