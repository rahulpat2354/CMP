package com.cis.cmp.data.network

import kotlinx.serialization.Serializable


/**
 * Base sealed class for all network-related exceptions
 */
sealed class NetworkException(
    open val statusCode: Int,
    open val title: String,
    open val detail: String? = null
) : Exception(detail ?: title)

class BadRequest(
    override val detail: String
) : NetworkException(400, "Bad Request", detail)

class Unauthorized(
    override val detail: String
) : NetworkException(401, "Unauthorized", detail)

class Forbidden(
    override val detail: String
) : NetworkException(403, "Forbidden", detail)

class NotFound(
    override val detail: String
) : NetworkException(404, "Not Found", detail)

class Timeout : NetworkException(408, "Timeout")

class NoInternet : NetworkException(-1, "No Internet")

class ServerError(
    override val statusCode: Int,
    override val detail: String?
) : NetworkException(statusCode, "Server Error", detail)

class ClientError(
    override val statusCode: Int,
    override val detail: String?
) : NetworkException(statusCode, "Client Error", detail)
