package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.service.ResponseHandler
import com.carlyu.apibackend.vo.ApiResponseVO

class ResponseHandlerImpl : ResponseHandler {
    override fun handleResponse(responseVO: ApiResponseVO): String {
        return responseVO.apiResponse
    }
}