package com.carlyu.apibackend.utils

import org.springframework.web.reactive.function.client.WebClient


object ApiCaller {

    private val webClient: WebClient = WebClient.create()

    fun callApi(apiUrl: String): String? {
        return webClient.get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}