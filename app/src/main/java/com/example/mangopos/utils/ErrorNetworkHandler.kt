package com.example.mangopos.utils

import com.example.mangopos.data.objects.dto.UsersResponse
import io.ktor.client.features.*
import java.lang.Error
import java.nio.channels.UnresolvedAddressException

fun <T> errorHandler(t: Throwable, ) : Resource<T> {
    when (t) {
        is ClientRequestException ->
            return if (t.response.status.value == 403) {
                Resource.Error<T>("403 invalid credential")
            } else {
                Resource.Error<T>("Unknown Client Errors")
            }
        is UnresolvedAddressException -> {
            return Resource.Error<T>("500 Internet Error, check your connection")
        }
        else -> {
            return Resource.Error<T>("Unknown Error")
        }
    }
}