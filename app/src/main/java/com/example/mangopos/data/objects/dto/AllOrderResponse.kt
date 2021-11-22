package com.example.mangopos.data.objects.dto


import android.view.Menu
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllOrderResponse(
    @SerialName("currentPage")
    val currentPage: String,
    @SerialName("data")
    val orderData: List<OrderData>,
    @SerialName("limit")
    val limit: String,
    @SerialName("totalData")
    val totalData: Int,
    @SerialName("totalPage")
    val totalPage: Int
)

@Serializable
data class OrderData(
    @SerialName("menus")
    val carts: List<Cart>,
    @SerialName("name")
    val name: String,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("status")
    val status: String,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class Cart(
    @SerialName("image")
    val image: String,
    @SerialName("menu_uuid")
    val menuUuid: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class SingleOrderResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("menus")
    val menus: List<Cart>,
    @SerialName("name")
    val name: String,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("status")
    val status: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("uuid")
    val uuid: String
)