package com.carlyu.apibackend.vo

import java.io.Serializable

data class ResultVO(
    val success: Boolean? = null,
    val code: Int,
    val msg: String = "",
    val data: Any? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}