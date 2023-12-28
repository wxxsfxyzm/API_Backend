package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.service.APIRemoteAccess
import com.carlyu.apibackend.utils.ApiCaller
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class APIRemoteAccessImpl : APIRemoteAccess {
    override fun visitRemoteAPI(url: String, key: String): String {
        val finalURL = url + key
        val response = ApiCaller.callApi(finalURL)
        jacksonObjectMapper().readTree(response)
        return response!!
    }
}