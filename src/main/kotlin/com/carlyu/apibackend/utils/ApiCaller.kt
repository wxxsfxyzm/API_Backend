package com.carlyu.apibackend.utils

import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import org.springframework.web.reactive.function.client.WebClient


object ApiCaller {

    private val webClient: WebClient = WebClient.create()

    fun callApi(apiUrl: String, requestBody: GoogleApiRequestDTO): String? {
        return webClient.post()
            .uri(apiUrl)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}