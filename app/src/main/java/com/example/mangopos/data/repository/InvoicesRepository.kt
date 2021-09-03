package com.example.mangopos.data.repository

import com.example.mangopos.data.EndPoints
import com.example.mangopos.data.objects.dto.ChartItem
import com.example.mangopos.data.objects.dto.InvoicesResponse
import com.example.mangopos.data.objects.dto.OrderResponse
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
            api.get<InvoicesResponse>("${EndPoints.BASE_URL}${EndPoints.INVOICES_URL}?page=1") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun getChartData(accessToken : String): Resource<ChartItem> {
        val response = try {
            api.get<ChartItem>("${EndPoints.BASE_URL}${EndPoints.INVOICES_URL}/chart") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }
}