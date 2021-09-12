package com.example.mangopos.data.repository

import com.example.mangopos.data.EndPoints
import com.example.mangopos.data.objects.dto.*
import com.example.mangopos.utils.Resource
import com.example.mangopos.utils.errorHandler
import dagger.hilt.android.scopes.ActivityScoped
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*


@ActivityScoped
class OrderRepository(
    private val api: HttpClient
) {

    suspend fun getListOrder(accessToken : String, page:Int): Resource<OrderResponse> {
        val response = try {
            api.get<OrderResponse>("${EndPoints.BASE_URL}${EndPoints.ORDER_URL}page=$page") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")

            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun getOrder(accessToken: String, orderUUID:String) : Resource<SingleOrderResponse>{
        val response = try {
            api.get<SingleOrderResponse>("${EndPoints.BASE_URL}${EndPoints.SINGLE_ORDER_URL}$orderUUID") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")

            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun newOrder(accessToken: String, singleOrderRequest: SingleOrderRequest) : Resource<SuccessOrderResponse>{
        val response = try {
            api.post<SuccessOrderResponse>("${EndPoints.BASE_URL}${EndPoints.NEW_ORDER}") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
                body = singleOrderRequest

            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun updateOrder(accessToken: String, singleOrderRequest: SingleOrderRequest, orderUuid:String) : Resource<SuccessOrderResponse>{
        val response = try {
            api.patch<SuccessOrderResponse>("${EndPoints.BASE_URL}${EndPoints.CART_URL}${orderUuid}") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
                body = singleOrderRequest

            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun payOrder(accessToken: String, payRequest: PayRequest, uuid:String) : Resource<SuccessOrderResponse>{
        val response = try {
            api.post<SuccessOrderResponse>("${EndPoints.BASE_URL}${EndPoints.INVOICES_URL}/$uuid") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
                body = payRequest

            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }

    suspend fun getNoInvoices(accessToken: String, noInvoices:String) : Resource<PaymentInvoicesResponse>{
        val response = try {
            api.get<PaymentInvoicesResponse>("${EndPoints.BASE_URL}${EndPoints.INVOICES_URL}/$noInvoices") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer $accessToken")
            }
        }catch (t:Throwable){
            return errorHandler(t)
        }

        return Resource.Success(response)
    }
}