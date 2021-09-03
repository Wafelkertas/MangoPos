package com.example.mangopos.data.objects.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    @SerializedName("user_data")
    val userData: UserData? = null,
    val user: User
)
@Serializable
data class UserData(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
@Serializable
data class User(
    @SerializedName("access_token")
    val access_token: String
)

@Serializable
data class UserRequest(
    @SerializedName("username")
    val username:String,
    @SerializedName("password")
    val password:String
)

