package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.service.APIRemoteResponseHandler
import com.carlyu.apibackend.vo.ApiResponseVO
import org.springframework.stereotype.Service

@Service
class APIRemoteResponseHandlerImpl : APIRemoteResponseHandler {
    override fun handleResponse(responseVO: ApiResponseVO): String {
        return responseVO.apiResponse
    }
}