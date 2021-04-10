package com.example.myhomeapp.remote

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>,
): Resource<T> {
    return withContext(dispatcher) {
        val resource: Resource<T>
        resource = try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                successData(response)
            } else {
                errorDataAsync(response)
            }
        } catch (t: Throwable) {
            exceptionDataAsync(t)
        }
        resource
    }
}

fun exceptionData(error: Throwable): ErrorEntity = when {
    error is SocketTimeoutException ->
        ErrorEntity(ErrorEntity.ERROR_CODE_TIMEOUT)

    error is UnsupportedOperationException && error.message == "JsonNull" ->
        ErrorEntity(ErrorEntity.ERROR_CODE_NO_CONTENT)

    error is UnknownHostException ->
        ErrorEntity(ErrorEntity.ERROR_NO_INTERNET)

    else -> ErrorEntity(ErrorEntity.ERROR_UNKNOWN_EXCEPTION).apply {
        errorDesc = error.message
        error.printStackTrace()
    }
}

fun <T> errorData(response: Response<T>): ErrorEntity {
    val gson = Gson()
    var errorBodyString = ""
    response.errorBody()?.let {
        errorBodyString = it.string()
    }
    //TODO: Process error codes
    val errorEntity: ErrorEntity = ErrorEntity(ErrorEntity.ERROR_UNKNOWN_EXCEPTION)
    val errorBody = response.errorBody()?.string()
    if (errorBody != null) {
        try {
            errorEntity.errorDesc =
                gson.fromJson(errorBody, NetworkErrorResponse::class.java)
                    .errors._global.first().message
        } catch (ex: Throwable) {
            Log.d("CoroutineHandler", "ErrorBody unknown format")
        }
    }
    return errorEntity
}

fun <T> exceptionDataAsync(t: Throwable) = Resource.error<T>(null, exceptionData(t))
fun <T> errorDataAsync(response: Response<T>) = Resource.error<T>(null, errorData(response))


fun <T> successData(response: Response<T>): Resource<T> {
    return if (response.code() != 204 && response.body() == null) {
        Resource.error(null, ErrorEntity(ErrorEntity.ERROR_CODE_NO_CONTENT))
    } else {
        Resource.success(response.body())
    }
}