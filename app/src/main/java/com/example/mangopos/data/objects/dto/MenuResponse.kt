package com.example.mangopos.data.objects.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    @SerialName("currentPage")
    val currentPage: String,
    @SerialName("data")
    val menuData: List<MenuItem>,
    @SerialName("limit")
    val limit: String,
    @SerialName("totalData")
    val totalData: Int,
    @SerialName("totalPage")
    val totalPage: Int
)

@Serializable
data class MenuItem(
    @SerialName("image")
    val image: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("slug")
    val slug: String,
    @SerialName("menu_uuid")
    val menuUuid: String,
    @SerialName("quantity")
    val quantity: Int
)

@Serializable
data class UpdateMenuResponse(
    @SerialName("success")
    val success:String
)