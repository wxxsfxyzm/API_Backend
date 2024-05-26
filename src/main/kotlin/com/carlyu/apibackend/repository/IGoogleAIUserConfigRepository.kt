package com.carlyu.apibackend.repository

import com.carlyu.apibackend.entity.GoogleAIUserConfig
import org.springframework.data.jpa.repository.JpaRepository

interface IGoogleAIUserConfigRepository : JpaRepository<GoogleAIUserConfig, Long>