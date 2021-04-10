package com.example.myhomeapp.remote

sealed class Resource<T> private constructor(
    val data: T?,
    val errorEntity: ErrorEntity?
) {
    companion object {
        fun <T> success(data: T?) = SuccessResource(data)
        fun <T> error(data: T?, entity: ErrorEntity) = ErrorResource(data, entity)
    }
}

class SuccessResource<T>(data: T?) : Resource<T>(data, null)
class ErrorResource<T>(data: T?, entity: ErrorEntity) : Resource<T>(data, entity)