package com.carlyu.apibackend.service

import com.carlyu.apibackend.vo.ApiResponseVO

interface APIRemoteResponseHandler {
    fun handleResponse(responseVO: ApiResponseVO): String
}