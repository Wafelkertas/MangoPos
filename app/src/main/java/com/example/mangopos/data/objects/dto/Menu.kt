package com.example.mangopos.data.objects.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    @SerialName("data")
    val data: MenuData
)

@Serializable
data class MenuLink(
    @SerialName("active")
    val active: Boolean,
    @SerialName("label")
    val label: String,
    @SerialName("url")
    val url: String?
)

@Serializable
data class MenuItem(
    @SerialName("category_uuid")
    val categoryUuid: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class MenuData(
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("data")
    val menuItem: List<MenuItem>,
    @SerialName("first_page_url")
    val firstPageUrl: String,
    @SerialName("from")
    val from: Int,
    @SerialName("last_page")
    val lastPage: Int,
    @SerialName("last_page_url")
    val lastPageUrl: String,
    @SerialName("links")
    val links: List<MenuLink>,
    @SerialName("next_page_url")
    val nextPageUrl: String?,
    @SerialName("path")
    val path: String,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("prev_page_url")
    val prevPageUrl:  String?,
    @SerialName("to")
    val to: Int,
    @SerialName("total")
    val total: Int
)

@Serializable
data class CategoryResponse(
    @SerialName("data")
    val listOfCategory: List<Category>
)

@Serializable
data class Category(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class UpdateMenuResponse(
    @SerialName("data")
    val data:String
)

@Serializable
data class CreateMenuResponse(
    @SerialName("data")
    val data:String
)