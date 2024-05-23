package com.carlyu.apibackend.enums

import com.carlyu.apibackend.constant.BaseConstant

enum class MODEL(val model: String) {
    GEMINI_PRO(BaseConstant.GOOGLE_GEMINI_MODEL_GEMINI_PRO),
    GEMINI_PRO_VISION(BaseConstant.GOOGLE_GEMINI_MODEL_GEMINI_PRO_VISION),
    GEMINI_1_5_PRO(BaseConstant.GOOGLE_GEMINI_MODEL_GEMINI_1_5_PRO)
}