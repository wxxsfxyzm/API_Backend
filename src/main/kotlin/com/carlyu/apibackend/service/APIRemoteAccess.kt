package com.carlyu.apibackend.service

import com.carlyu.apibackend.dto.GoogleApiRequestDTO


interface APIRemoteAccess {

    fun visitRemoteAPI(remoteHost: String, remotePath: String, key: String, requestBody: GoogleApiRequestDTO): String
}