package com.example.mangopos.data.objects.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    @SerialName("data")
    var orderItem: List<OrderItem>,
    @SerialName("last_page")
    val lastPage: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("total_data")
    val totalData: Int
)

@Serializable
data class OrderItem(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("customer_name")
    val customerName: String,
    @SerialName("id")
    val id: Int,
    @SerialName("status")
    val status: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class SingleOrderResponse(
    @SerialName("carts")
    val carts: List<Cart>,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("customer_name")
    val customerName: String,
    @SerialName("status")
    val status: String,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class Cart(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("menu_name")
    val menuName: String,
    @SerialName("menu_uuid")
    val menuUuid: String,
    @SerialName("order_uuid")
    val orderUuid: String,
    @SerialName("price")
    val price: String,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class SingleOrderRequest(
    @SerialName("carts")
    val carts: List<Cart>,
    @SerialName("customer_name")
    val customerName: String,

    )



@Serializable
data class SuccessOrderResponse(
    @SerialName("data")
    val successUpdate: Boolean
)