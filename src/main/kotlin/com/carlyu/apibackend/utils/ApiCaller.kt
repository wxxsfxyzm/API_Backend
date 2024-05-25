package com.carlyu.apibackend.utils


import com.carlyu.apibackend.dto.GoogleApiRequestDTO
import com.carlyu.apibackend.dto.GoogleApiRequestDTO.Companion.toJsonString
import org.springframework.web.reactive.function.client.WebClient


object ApiCaller {

    private val webClient: WebClient = WebClient.create()
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)
    fun callApi(apiHost: String, apiPath: String, apikey: String, requestBody: GoogleApiRequestDTO): String? {
        log.info("apiHost: $apiHost apiPath: $apiPath apikey: $apikey requestBody: $requestBody")
        val bodyValue = requestBody.toJsonString()
        log.info("bodyValue: $bodyValue")
        return webClient.post()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("https")
                    .host(apiHost)
                    .path(apiPath)
                    .queryParam("key", apikey)
                    .build()
            }
            .header("Content-Type", "application/json")
            .bodyValue(requestBody.toJsonString())
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}