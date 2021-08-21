package com.example.mangopos.data.objects.dto

data class UsersResponse(
    val data: Data,
    val user: User
)

data class Data(
    val email: String,
    val id: Int,
    val name: String
)

data class User(
    val access_token: String
)

data class UserRequest(
    val username:String,
    val password:String
)

