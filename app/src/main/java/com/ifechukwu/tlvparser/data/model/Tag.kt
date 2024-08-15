package com.ifechukwu.tlvparser.data.model

/**
 * @Author: ifechukwu.udorji
 * @Date: 8/14/2024
 */
data class Tag(
    val tag: String,
    val length: Int,
    val value: String,
    val tagDescription: String? = null,
)