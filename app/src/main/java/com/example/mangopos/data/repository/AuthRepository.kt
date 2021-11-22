package com.example.mangopos.data.repository

import com.example.mangopos.data.EndPoints.BASE_URL
import com.example.mangopos.data.EndPoints.LOGIN_URL
import com.example.mangopos.data.objects.dto.User
import com.example.mangopos.data.objects.dto.UserRequest
import com.example.mangopos.data.objects.dto.UsersResponse
import com.example.mangopos.utils.Resource
import com.example.mangopos.utils.errorHandler
import dagger.hilt.android.scopes.ActivityScoped
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.nio.channels.UnresolvedAddressException

@ActivityScoped
class AuthRepository(
    private val api: HttpClient
) {

    suspend fun repositoryLogin(username:String, password:String): Resource<User> {

        val response = try {

             api.post<User>("$BASE_URL$LOGIN_URL") {
                 header(HttpHeaders.ContentType, ContentType.Application.Json)
                body = UserRequest(username = username, password = password)
            }

        } catch (t: Throwable) {

           return errorHandler(t)

        }

        return Resource.Success(response)
    }


}