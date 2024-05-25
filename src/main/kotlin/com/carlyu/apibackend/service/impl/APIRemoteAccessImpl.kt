package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import com.carlyu.apibackend.service.APIRemoteAccess
import com.carlyu.apibackend.utils.ApiCaller
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientResponseException


@Service
class APIRemoteAccessImpl : APIRemoteAccess {
    override fun visitRemoteAPI(
        remoteHost: String,
        remotePath: String,
        key: String,
        requestBody: GoogleApiRequestDTO
    ): String {
        // val finalURL = url + key
        try {
            log.info("remoteHost: $remoteHost remotePath: $remotePath key: $key requestBody: $requestBody")
            val response = ApiCaller.callApi(remoteHost, remotePath, key, requestBody)
            log.info("response: $response")

            // this is full json object
            //val jacksonObject = jacksonObjectMapper().readTree(response)
            //log.info("jacksonObject: $jacksonObject")

            return response!!
        } catch (ex: WebClientResponseException) {
            println("HTTP status code: ${ex.statusCode}")
            println("Error calling API: ${ex.message}")
            println("Response body: ${ex.responseBodyAsString}")

            throw ex // re-throw the exception if you want to handle it further up the stack

        }
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}