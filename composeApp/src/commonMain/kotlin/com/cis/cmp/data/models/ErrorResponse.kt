package com.cis.cmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val errorMessage: ErrorDetail? = null
)

@Serializable
data class ErrorDetail(
    val code: String,
    val details: String
)