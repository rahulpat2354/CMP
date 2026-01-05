package com.cis.cmp.data.network

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: NetworkException) {
        ApiResult.Error(e)
    } catch (e: Throwable) {
        ApiResult.Error(
            ServerError(
                statusCode = -2,
                detail = e.message ?: "Unexpected error"
            )
        )
    }
}
