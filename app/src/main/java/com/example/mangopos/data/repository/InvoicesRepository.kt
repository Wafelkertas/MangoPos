package com.example.mangopos.data.repository

import com.example.mangopos.data.EndPoints
import com.example.mangopos.data.objects.dto.Chart
import com.example.mangopos.data.objects.dto.InvoicesResponse
import com.example.mangopos.utils.Resource
import com.example.mangopos.utils.errorHandler
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class InvoicesRepository(
    private val api : HttpClient
) {

    suspend fun getListInvoices(accessToken : String): Resource<InvoicesResponse> {
        val response = try {
            api.get<InvoicesResponse>("${EndPoints.BASE_URL}${EndPoints.INVOICES_URL}?sort=desc") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun getAnotherListInvoices(accessToken : String, page:Int): Resource<InvoicesResponse> {
        val response = try {
            api.get<InvoicesResponse>("${EndPoints.BASE_URL}${EndPoints.INVOICES_URL}?page=$page&?sort=desc") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun getChartData(accessToken : String): Resource<Chart> {
        val response = try {
            api.get<Chart>("${EndPoints.BASE_URL}${EndPoints.INVOICES_URL}/chart") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }
}