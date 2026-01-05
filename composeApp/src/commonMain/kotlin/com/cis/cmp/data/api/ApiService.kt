package com.cis.cmp.data.api

import com.cis.cmp.data.models.LoginRequest
import com.cis.cmp.data.models.LoginResponse
import com.cis.cmp.data.models.MainDashboardResp
import com.cis.cmp.data.network.ApiResult
import com.cis.cmp.data.network.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiService(private val httpClient: HttpClient) {

    private val baseUrl = "https://rtvk5001.elb.cisinlive.com"

    /*suspend fun getUsers(): List<User> {
        return httpClient.get("$baseUrl/users").body()
    }

    suspend fun getUserById(id: Int): User {
        return httpClient.get("$baseUrl/users/$id").body()
    }*/


    suspend fun login(email: String, password: String): ApiResult<LoginResponse> = safeApiCall {
        httpClient.post("$baseUrl/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }.body()
    }

    suspend fun getDashboardOverviewData(): ApiResult<MainDashboardResp> = safeApiCall {
        httpClient.get("$baseUrl/api/user/activities/getDashboardOverview") {
            contentType(ContentType.Application.Json)
        }.body()
    }

}
