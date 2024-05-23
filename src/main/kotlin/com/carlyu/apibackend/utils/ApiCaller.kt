package com.carlyu.apibackend.utils


import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import com.carlyu.apibackend.dto.GoogleApiRequestDTO.Companion.toJsonString
import org.springframework.web.reactive.function.client.WebClient


object ApiCaller {

    private val webClient: WebClient = WebClient.create()
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    fun callApi(apiUrl: String, requestBody: GoogleApiRequestDTO): String? {
        log.info(requestBody.toJsonString())
        return webClient.post()
            .uri(apiUrl)
            .header("Content-Type", "application/json")
            .bodyValue(requestBody.toJsonString())
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }


}