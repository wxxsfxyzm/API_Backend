package com.carlyu.apibackend.enums

import com.carlyu.apibackend.constant.BaseConstant

enum class URL(val url: String) {
    GOOGLE_GEMINI_API(BaseConstant.getGoogleGeminiApiUrl(BaseConstant.GOOGLE_GEMINI_MODEL_GEMINI_PRO)),
}