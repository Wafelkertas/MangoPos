package com.example.mangopos.data.repository

import android.util.Log
import com.example.mangopos.data.EndPoints
import com.example.mangopos.data.objects.dto.*
import com.example.mangopos.utils.Resource
import com.example.mangopos.utils.errorHandler
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.io.File

class MenuRepository(
    private val api: HttpClient
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

    suspend fun getListCategory(accessToken: String): Resource<CategoryResponseItem> {
        val response = try {
            api.get<CategoryResponseItem>("${EndPoints.BASE_URL}${EndPoints.CATEGORY}") {
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
        menuItem: com.example.mangopos.data.objects.dto.MenuItem,
        uri: String
    ): Resource<UpdateMenuResponse> {

        val response = try {
            api.post<UpdateMenuResponse>("${EndPoints.BASE_URL}${EndPoints.MENU_URL_PATCH}/${menuItem.menuUuid}") {
                header("Authorization", "Bearer $accessToken")
                body = MultiPartFormDataContent(
                    formData {
                        append("price", "${menuItem.price}")
                        append("name", "${menuItem.name}")
                        append("category_uuid", "${menuItem.menuUuid}")
                        if (uri.isNotEmpty()){
                            append("image", File(uri).readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, ContentType.Image.JPEG)
                                append(HttpHeaders.ContentDisposition, "filename=$uri")
                            })
                        }
                        if (uri.isEmpty()){
                            append("image", "")
                        }


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
        menuItem: com.example.mangopos.data.objects.dto.MenuItem,
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
                        append("category_uuid", "${menuItem.menuUuid}")
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