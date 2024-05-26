package com.carlyu.apibackend.exceptions

import com.carlyu.apibackend.enums.ResultEnum

class ApiResponseStatusException(
    val code: Int?,
    message: String
) : RuntimeException(message) {
    constructor(
        resultEnum: ResultEnum
    ) : this(
        resultEnum.code,
        resultEnum.message
    )
}
