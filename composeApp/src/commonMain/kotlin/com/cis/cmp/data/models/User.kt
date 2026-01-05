package com.cis.cmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LocalUserData(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "",
    val email: String = "",
    val phone: String = "",
    val language: String = "",
    val isVerified: Boolean = false,
    val gender: String = "",
    val dateOfBirth: String = "",
    val nationality: String = "",
    val addressLine1: String = "",
    val addressLine2: String = "",
    val apartment: String = "",
    val suburb: String = "",
    val city: String = "",
    val province: String = "",
    val zip: String = "",
    val country: String = "",
    val companyName: String = "",
    val designation: String = "",
    val image: String = "",
    val authToken: String = "",
    val refreshToken: String = ""
)

fun LoginResponse.Data.toLocalUserData(): LocalUserData {
    return LocalUserData(
        id = id,
        firstName = firstName,
        lastName = lastName,
        role = role,
        email = email,
        phone = phone,
        language = language,
        isVerified = isVerified,
        gender = gender,
        dateOfBirth = dateOfBirth,
        nationality = nationality,
        addressLine1 = addressLine1,
        addressLine2 = addressLine2,
        apartment = apartment,
        suburb = suburb,
        city = city,
        province = province,
        zip = zip,
        country = country,
        companyName = companyName,
        designation = designation,
        image = image,
        authToken = authToken,
        refreshToken = refreshToken
    )
}
