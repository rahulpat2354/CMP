package com.cis.cmp.data.network

import com.cis.cmp.data.models.ErrorResponse
import com.cis.cmp.data.preference.PreferenceManager
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object JsonProvider {
    val instance = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = false
        explicitNulls = false
    }
}


object HttpClientFactory {
    fun create(preferenceManager: PreferenceManager? = null): HttpClient{
        return HttpClient {
            install(ContentNegotiation){
                json(JsonProvider.instance)
            }

            install(Logging){
                logger = object : Logger {
                    override fun log(message: String) {
                        // Example using Kermit (recommended KMP logging library)
                        co.touchlab.kermit.Logger.v{
                            message
                        }
                    }
                }
                level = LogLevel.BODY
            }

            install(HttpTimeout) {
                val timeout = 60000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }

            defaultRequest {
                preferenceManager?.getAuthToken()?.let { token ->
                    header(HttpHeaders.Authorization, "Bearer $token")
                }

                header(HttpHeaders.ContentType, "application/json")
                header(HttpHeaders.Accept, "application/json")
            }

            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status.value >= 400) {
                        val error = response.bodyAsText().decodeError()

                        throw when (response.status.value) {
                            400 -> BadRequest(error)
                            401 -> Unauthorized(error)
                            403 -> Forbidden(error)
                            404 -> NotFound(error)
                            408 -> Timeout()
                            in 500..599 -> ServerError(response.status.value, error)
                            else -> ServerError(response.status.value, error)
                        }
                    }
                }

                // Handle specific exceptions during request
                handleResponseExceptionWithRequest { exception, request ->
                    val clientException = when(exception){
                        is ClientRequestException -> {
                            val errorBody = exception.response.bodyAsText()
                            ClientError(exception.response.status.value, errorBody)
                        }
                        is ServerResponseException -> {
                            ServerError(exception.response.status.value, "Server error")
                        }
                        else -> exception
                    }
                    throw clientException
                }
            }
        }
    }
}

fun String.decodeError(): String =
    try { JsonProvider.instance.decodeFromString<ErrorResponse>(this).errorMessage?.details ?: this }
    catch (_: Exception) { this }
