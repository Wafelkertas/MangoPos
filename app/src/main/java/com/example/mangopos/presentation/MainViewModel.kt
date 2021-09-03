package com.example.mangopos.presentation

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangopos.R
import com.example.mangopos.data.objects.dto.*
import com.example.mangopos.data.repository.AuthRepository
import com.example.mangopos.data.repository.InvoicesRepository
import com.example.mangopos.data.repository.MenuRepository
import com.example.mangopos.data.repository.OrderRepository
import com.example.mangopos.utils.Resource
import com.example.mangopos.utils.URIPathHelper

import com.example.mangopos.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository,
    private val invoicesRepository: InvoicesRepository,
    private val menuRepository: MenuRepository,
    private val dispatchers: DispatcherProvider,
    val sharedPreferences: SharedPreferences
) : ViewModel() {



    // Global State
    val accessToken = mutableStateOf("")
    val sharePref = mutableStateOf("")


    // Error Handling for Login
    val networkLoginErrorMessage = mutableStateOf("")
    val networkErrorLogin = mutableStateOf<Boolean?>(null)

    // Error Handling for Order
    val networkOrderErrorMessage = mutableStateOf("")
    val networkErrorOrder = mutableStateOf<Boolean?>(null)

    // Error Handling for Invoices
    val networkInvoicesErrorMessage = mutableStateOf("")
    val networkInvoicesError = mutableStateOf<Boolean?>(null)

    // Error Handling for Category
    val networkCategoryErrorMessage = mutableStateOf("")
    val networkCategoryError = mutableStateOf<Boolean?>(null)

    // Error Handling for Chart
    val networkChartErrorMessage = mutableStateOf("")
    val networkChartError = mutableStateOf<Boolean?>(null)

    val networkSingleOrderError = mutableStateOf<Boolean?>(null)
    val networkSingleOrderErrorMessage = mutableStateOf("")

    // Variable to hold drawer state edit order or new order
    val editOrder = mutableStateOf<Boolean?>(null)

    // State for list of Object
    val listOfOrder = mutableStateOf<List<OrderItem>>(listOf())
    val listOfMenu = mutableStateOf<List<MenuItem>>(listOf())
    val listOfInvoices = mutableStateOf<List<InvoicesItem>>(listOf())
    val listOfCategory = mutableStateOf<List<Category?>>(listOf(null))

    val orderResponse = mutableStateOf<OrderResponse?>(null)

    // State hold when new order created
    val listOfCartNewOrder = mutableStateOf<List<Cart>>(listOf())
    val newOrderStatus = mutableStateOf<Boolean?>(null)

    // State for hold when edit an order
    val singleOrderResponse = mutableStateOf<SingleOrderResponse?>(null)
    val singleListOfCarts = mutableStateOf<List<Cart>>(listOf())
    val updateOrderStatus = mutableStateOf<Boolean?>(null)

    val editMenu = mutableStateOf<MenuItem?>(null)

    var currentPage = mutableStateOf(1)
    var totalPage = mutableStateOf(0)
    var endReached = mutableStateOf(false)


    var orderUUID: String = ""


    init {
        viewModelScope.launch {


//            delay(500)
//            loadSharePrefToState()
//            invoicesRepository.getChartData("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiN2ZmMDJiYjg3M2I5MDg0ZGNiNmRiNmE4N2UzYWEwOWIwOGJjYTdiYzI1YzY2NmJmMWEyMWIxMmEwZmYyNjE4NjQxZjIzYWJkYWRjMDIyMzAiLCJpYXQiOjE2Mjk4NjgwNTUuNzY5NzA4LCJuYmYiOjE2Mjk4NjgwNTUuNzY5NzEsImV4cCI6MTY2MTQwNDA1NS43Njc2NCwic3ViIjoiMSIsInNjb3BlcyI6W119.CqCLuSXI_J1YsdJY6_e-7xmDFYaUa2eUt8jKNM6i6xKgDslxS0qzMoBde1300zpalWPGrAl6FUtYcmU47DnJZV3Q5uut5YEFoKklamD3zcDRoPkZrfHpYoj7Uvutjj1FKE_kduESm4Hdnqog_S1qCSFkL1rPJExsRpuYJAHsnbNJwqvtrol5TZKFKfDiWXpw8mJk9E7Fo5-b8IxXouVKX_gUS1i6wypZZcOURLuOImB_NcI5HdMF0cE9wmhZjDuNF2l-KWvVZhSp0_i_RusOBYA8joHNL9wy9eApmbWqvc1k4N5_soCvdZiWYQ_g-h3SaBmlbzlGXVJGva1HGi5T7rNFwOGteWFirYAYG_YwW60JBdYEWJfZ9uWry3PYpV6eB6G2STZvOAh2htU3XcS-HP5x7dyvCVUWrB5IECjBAaohMrN4L0eWxIE7ogIbHbCtA21SkHapsrysdVV9haKYiijzzg5x6e9p14DOpjIXyO206oXlXpFnyh3z740Xl8FPHkl5bQn7wvl5duVxWDnEC9VTX-0-Tc8QHgUEzAVe3SVo0h3RlrxeBYLDwMjcbIz1A36pR2D33R7jPyHDB-nGMK2j2clDW8jdNTilc09X0P8con2afRevU2UNv1P7oMgDbX-eFV2DrXwamZYl3XZIa--RQRUff8QDOFHzJBgAYu4")



        }
    }


    fun signIn(username: String, password: String) {
        viewModelScope.launch(dispatchers.io) {
            val response = authRepository.repositoryLogin(username = username, password = password)

            when (response) {
                is Resource.Success -> {
                    networkErrorLogin.value = false

                    with(sharedPreferences.edit()) {
                        putString(
                            R.string.access_token.toString(),
                            response.data!!.user.access_token
                        )
                        apply()
                    }
                    accessToken.value = response.data!!.user.access_token
                }

                is Resource.Error -> {
                    networkErrorLogin.value = true
                    networkLoginErrorMessage.value = response.message.toString()

                }
            }


        }
    }


    fun signOut() {
        viewModelScope.launch(dispatchers.io) {

            with(sharedPreferences.edit()) {
                putString(R.string.access_token.toString(), "")
                apply()
            }
            accessToken.value = ""
            networkErrorLogin.value = null
            sharePref.value = ""
        }
    }

    fun filterMenuToBeEdited(uuid:String){
        val menuEdit = listOfMenu.value.filter { menuItem ->
            menuItem.uuid == uuid
        }
        val newData = menuEdit.firstOrNull()

        editMenu.value = newData
    }



    fun getAllCategory(accessToken: String){
        viewModelScope.launch(dispatchers.io) {
            val response = menuRepository.getListCategory(accessToken = accessToken)

            when (response) {
                is Resource.Success -> {

                    listOfCategory.value = response.data!!.listOfCategory


                }

                is Resource.Error -> {
                    networkCategoryError.value = true
                    networkCategoryErrorMessage.value = response.message.toString()
                }
            }
        }
    }

    fun getInvoicesList(accessToken: String){
        viewModelScope.launch(dispatchers.io) {
            val response = invoicesRepository.getListInvoices(accessToken = accessToken)

            when (response) {
                is Resource.Success -> {

                    listOfInvoices.value = response.data!!.invoicesItem


                }

                is Resource.Error -> {
                    networkInvoicesError.value = true
                    networkInvoicesErrorMessage.value = response.message.toString()

                }
            }
        }
    }


    fun newOrder(singleOrderRequest: SingleOrderRequest) {
        viewModelScope.launch(dispatchers.io) {
            val response = orderRepository.newOrder(
                accessToken = accessToken.value,
                singleOrderRequest = singleOrderRequest
            )
            when (response) {
                is Resource.Success -> {

                    newOrderStatus.value = true
                    Log.d("newOrderStatusSucced", newOrderStatus.value.toString())

                }

                is Resource.Error -> {

                    newOrderStatus.value = false
                    Log.d("newOrderStatusFailed", newOrderStatus.value.toString())
                }
            }

        }
    }

    fun getAllOrder(accessToken: String) {
        var allResponse: List<OrderItem> = listOf()
        var lastPage = 0
        var currentPage = 1

        viewModelScope.launch(dispatchers.io) {
            val firstResponse = orderRepository.getListOrder(accessToken = accessToken, page = 1).data
            if (firstResponse != null) {
                lastPage = firstResponse.lastPage
            }
            while (currentPage <= lastPage) {
                val response =
                    orderRepository.getListOrder(accessToken = accessToken, page = currentPage).data
                allResponse += response!!.orderItem
                listOfOrder.value = allResponse
                currentPage++
            }


        }

    }

    fun editAnOrder(singleOrderRequest: SingleOrderRequest, orderUUID: String) {
        viewModelScope.launch(dispatchers.io) {
            val response = orderRepository.updateOrder(
                accessToken = accessToken.value,
                singleOrderRequest = singleOrderRequest,
                orderUuid = orderUUID
            )
            when (response) {
                is Resource.Success -> {

                    updateOrderStatus.value = true

                }

                is Resource.Error -> {

                    updateOrderStatus.value = false

                }
            }

        }
    }


    fun getSingleOrder(accessToken: String, orderUUID: String) {
        Log.d("getSingleCartInvoked", "getSingleCartInvoked")
        viewModelScope.launch(dispatchers.io) {
            val response =
                orderRepository.getOrder(accessToken = accessToken, orderUUID = orderUUID)
            when (response) {
                is Resource.Success -> {

                    singleOrderResponse.value = response.data!!
                    singleListOfCarts.value = response.data.carts


                }

                is Resource.Error -> {
                    networkSingleOrderError.value = true
                    networkSingleOrderErrorMessage.value = response.message.toString()

                }
            }
        }
    }

    fun getMenuList(accessToken: String) {
        viewModelScope.launch(dispatchers.io) {
            val response = menuRepository.getListMenu(accessToken = accessToken)
            when (response) {

                is Resource.Success -> {
                    listOfMenu.value = response.data!!.data.menuItem

                }

                is Resource.Error -> {

                    networkOrderErrorMessage.value = response.message.toString()

                }
            }
        }
    }

    @InternalAPI
    fun createMenu(accessToken: String, menuItem: MenuItem, uri : String){
        viewModelScope.launch(dispatchers.io) {
        val response = menuRepository.newMenu(accessToken = accessToken, menuItem = menuItem, uri = uri)
        Log.d("createMenuInvoked", "createMenuInvoked ${response.message} ")

        }
    }

    @InternalAPI
    fun updateMenu(accessToken: String, menuItem: MenuItem, uri : String){
        viewModelScope.launch(dispatchers.io) {
            val response = menuRepository.updateMenu(accessToken = accessToken, menuItem = menuItem, uri = uri)
            Log.d("createMenuInvoked", "createMenuInvoked ${response.message} ")

        }
    }


     fun loadSharePrefToState() {
        viewModelScope.launch(dispatchers.io) {

            val sharePrefData = sharedPreferences.getString(R.string.access_token.toString(), "")

            if (!sharePrefData.isNullOrBlank()) {
                sharePref.value = sharePrefData
                accessToken.value = sharePrefData
            }
        }
    }
}