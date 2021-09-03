package com.example.mangopos.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.mangopos.data.EndPoints
import com.example.mangopos.data.api.Api
import com.example.mangopos.data.objects.dto.CategoryResponse
import com.example.mangopos.data.objects.dto.MenuItem
import com.example.mangopos.data.objects.dto.MenuResponse
import com.example.mangopos.data.objects.dto.UpdateMenuResponse
import com.example.mangopos.utils.Resource
import com.example.mangopos.utils.errorHandler
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.streams.*
import kotlinx.coroutines.runBlocking
import java.io.File
import java.net.URI

class MenuRepository(
    private val api: HttpClient,
    private val retrofit: Api,
    private val imageLoader: ImageLoader,
    private val context: Context
) {
    suspend fun getListMenu(accessToken: String): Resource<MenuResponse> {
        val response = try {
            api.get<MenuResponse>("${EndPoints.BASE_URL}${EndPoints.MENU_URL}") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        } catch (t: Throwable) {
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun getListCategory(accessToken: String): Resource<CategoryResponse> {
        val response = try {
            api.get<CategoryResponse>("${EndPoints.BASE_URL}${EndPoints.CATEGORY}") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        } catch (t: Throwable) {
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun updateMenu(
        accessToken: String,
        menuItem: MenuItem,
        uri: String
    ): Resource<UpdateMenuResponse> {


        val response = try {
            api.post<UpdateMenuResponse>("${EndPoints.BASE_URL}${EndPoints.MENU_URL_PATCH}/${menuItem.uuid}") {
                header("Authorization", "Bearer $accessToken")
                body = MultiPartFormDataContent(
                    formData {
                        append("price", "${menuItem.price}")
                        append("name", "${menuItem.name}")
                        append("description", "${menuItem.description}")
                        append("category_uuid", "${menuItem.categoryUuid}")
                        append("image", File(uri).readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Image.JPEG)
                            append(HttpHeaders.ContentDisposition, "filename=$uri")
                        })


                    }
                )

            }

        } catch (t: Throwable) {
            return errorHandler(t)
        }

        return Resource.Success(response)
    }


    suspend fun newMenu(
        accessToken: String,
        menuItem: MenuItem,
        uri: String
    ): Resource<UpdateMenuResponse> {

        val image = File(uri)
        val response = try {
            Log.d("MenuRepositoryInvoked", "createMenuRepositoryInvoked $image")
            api.post<UpdateMenuResponse>(
                "${EndPoints.BASE_URL}${EndPoints.MENU_URL_PATCH}"
            ) {
                header("Authorization", "Bearer $accessToken")
                body = MultiPartFormDataContent(
                    formData {
                        append("price", "${menuItem.price}")
                        append("name", "${menuItem.name}")
                        append("description", "${menuItem.description}")
                        append("category_uuid", "${menuItem.categoryUuid}")
                        append("image", image.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Image.JPEG)
                            append(HttpHeaders.ContentDisposition, "filename=$uri")
                        })

                    }
                )

            }
        } catch (t: Throwable) {
            Log.d("response", t.message.toString())
            return errorHandler(t)
        }
        return Resource.Success(response)
    }


}