package com.example.myhomeapp.remote

class ErrorEntity(var errorCode: String?) : Throwable() {

    var errorDesc: String? = null

    val text: String
        get() = if (errorDesc.isNullOrEmpty()) errorCode + "" else "$errorDesc"

    companion object{
        const val ERROR_CODE_TIMEOUT = "ERROR_CODE_TIMEOUT"
        const val ERROR_CODE_NO_CONTENT = "ERROR_CODE_NO_CONTENT"
        const val ERROR_CODE_BAD_REQUEST = "ERROR_CODE_BAD_REQUEST"
        const val ERROR_CODE_SERVER_EXCEPTION = "ERROR_CODE_SERVER_EXCEPTION"
        const val ERROR_UNKNOWN_EXCEPTION = "ERROR_UNKNOWN_EXCEPTION"
        const val ERROR_NO_INTERNET = "ERROR_NO_INTERNET"
    }
}