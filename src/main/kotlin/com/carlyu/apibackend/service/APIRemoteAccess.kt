package com.carlyu.apibackend.service

interface APIRemoteAccess {

    fun visitRemoteAPI(url: String, key: String): String
}