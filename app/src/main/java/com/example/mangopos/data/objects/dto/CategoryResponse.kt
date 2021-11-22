package com.example.mangopos.data.objects.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class CategoryResponseItem(
    @SerialName("data")
    val data: List<CategoryItem>,

)

@Serializable
data class CategoryItem(
    @SerialName("name")
    val name: String,
    @SerialName("uuid")
    val uuid: String
)