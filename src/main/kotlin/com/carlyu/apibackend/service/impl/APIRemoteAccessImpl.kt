package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import com.carlyu.apibackend.service.APIRemoteAccess
import com.carlyu.apibackend.utils.ApiCaller
import org.springframework.stereotype.Service


@Service
class APIRemoteAccessImpl : APIRemoteAccess {
    override fun visitRemoteAPI(url: String, key: String, requestBody: GoogleApiRequestDTO): String {
        val finalURL = url + key
        val response = ApiCaller.callApi(finalURL, requestBody)
        log.info("response: $response")

        // this is full json object
        //val jacksonObject = jacksonObjectMapper().readTree(response)
        //log.info("jacksonObject: $jacksonObject")

        return response!!
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}