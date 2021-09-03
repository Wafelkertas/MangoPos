package com.example.mangopos.data.api

import com.example.mangopos.data.objects.dto.UpdateMenuResponse
import com.example.mangopos.data.objects.dto.UserRequest
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface Api {


    @Multipart
    @POST("menus")
    suspend fun createMenu(
        @Part("name") name :String,
        @Part("category_uuid") categoryUuid :String,
        @Part("price") price :String,
        @Part("description") description :String,
    ) : UpdateMenuResponse


}