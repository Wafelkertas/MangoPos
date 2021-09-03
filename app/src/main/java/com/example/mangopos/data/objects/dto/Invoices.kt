package com.example.mangopos.data.objects.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoicesResponse(
    @SerialName("data")
    val invoicesItem: List<InvoicesItem>,
    @SerialName("last_page")
    val lastPage: Int,
    @SerialName("page")
    val page: String,
    @SerialName("total_data")
    val totalData: Int
)

@Serializable
data class InvoicesItem(
    @SerialName("cash")
    val cash: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("money_change")
    val moneyChange: String,
    @SerialName("name")
    val name: String,
    @SerialName("no_invoice")
    val noInvoice: String,
    @SerialName("order_uuid")
    val orderUuid: String,
    @SerialName("quantity")
    val quantity: String,
    @SerialName("total_price")
    val totalPrice: String,
    @SerialName("total_price_after_discount")
    val totalPriceAfterDiscount: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("uuid")
    val uuid: String
)