package com.cis.cmp.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("success") val success: Boolean = false,
    @SerialName("message") val message: String = "",
    @SerialName("errorMessage") val errorMessage: ErrorMessage = ErrorMessage(),
    @SerialName("data") val `data`: Data = Data()
) {
    @Serializable
    class ErrorMessage

    @Serializable
    data class Data(
        @SerialName("id") val id: Int = 0,
        @SerialName("firstName") val firstName: String = "",
        @SerialName("lastName") val lastName: String = "",
        @SerialName("role") val role: String = "",
        @SerialName("email") val email: String = "",
        @SerialName("phone") val phone: String = "",
        @SerialName("language") val language: String = "",
        @SerialName("isVerified") val isVerified: Boolean = false,
        @SerialName("gender") val gender: String = "",
        @SerialName("dateOfBirth") val dateOfBirth: String = "",
        @SerialName("nationality") val nationality: String = "",
        @SerialName("addressLine1") val addressLine1: String = "",
        @SerialName("addressLine2") val addressLine2: String = "",
        @SerialName("apartment") val apartment: String = "",
        @SerialName("suburb") val suburb: String = "",
        @SerialName("city") val city: String = "",
        @SerialName("province") val province: String = "",
        @SerialName("zip") val zip: String = "",
        @SerialName("country") val country: String = "",
        @SerialName("companyName") val companyName: String = "",
        @SerialName("designation") val designation: String = "",
        @SerialName("image") val image: String = "",
        @SerialName("authToken") val authToken: String = "",
        @SerialName("refreshToken") val refreshToken: String = ""
    )
}