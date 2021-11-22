package com.example.mangopos.presentation

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangopos.R
import com.example.mangopos.data.objects.dto.*
import com.example.mangopos.data.objects.model.PDFObject
import com.example.mangopos.data.repository.AuthRepository
import com.example.mangopos.data.repository.InvoicesRepository
import com.example.mangopos.data.repository.MenuRepository
import com.example.mangopos.data.repository.OrderRepository
import com.example.mangopos.utils.Resource

import com.example.mangopos.utils.dispatcher.DispatcherProvider
import com.example.mangopos.utils.newAddedToList
import com.tejpratapsingh.pdfcreator.utils.FileManager
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.basic.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Error
import java.lang.Exception
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

    val isRefresing: StateFlow<Boolean?> = MutableStateFlow(null)


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

    val networkPayOrderError = mutableStateOf<Boolean?>(null)

    // Error Handling for Chart
    val networkChartErrorMessage = mutableStateOf("")
    val networkChartError = mutableStateOf<Boolean?>(null)

    val networkSingleOrderError = mutableStateOf<Boolean?>(null)
    val networkSingleOrderErrorMessage = mutableStateOf("")

    val updateMenuStatus = mutableStateOf<Boolean?>(null)
    val createMenuStatus = mutableStateOf<Boolean?>(null)


    // Variable to hold drawer state edit order or new order
    val editOrder = mutableStateOf<Boolean?>(null)
    val checkoutOrder = mutableStateOf<Boolean>(false)

    // State for list of Object
    val listOfChartItem = mutableStateOf<List<ChartItem>>(listOf())
    val listOfOrder = mutableStateOf<List<OrderData>>(listOf())
    val listOfMenu = mutableStateOf<List<MenuItem>>(listOf())
    val listOfInvoices = mutableStateOf<List<InvoicesItem>>(listOf())
    val listOfCategory = mutableStateOf<List<CategoryItem?>>(listOf(null))

    val invoicesResponse = mutableStateOf<InvoicesResponse?>(null)

    val orderResponse = mutableStateOf<AllOrderResponse?>(null)

    // State hold when new order created
    val listOfCartNewOrder = mutableStateOf<List<MenuItem>>(listOf())
    val newOrderStatus = mutableStateOf<Boolean?>(null)

    // State for hold when edit an order
    val singleOrderResponse = mutableStateOf<SingleOrderResponse?>(null)
    val singleListOfCarts = mutableStateOf<List<Cart>>(listOf())
    val updateOrderStatus = mutableStateOf<Boolean?>(null)

    val editMenu = mutableStateOf<MenuItem?>(null)
    val invoicesDetail = mutableStateOf<PaymentInvoicesResponse?>(null)
    val invoicesEntree = mutableStateOf<InvoicesItem?>(null)

    var currentPage = mutableStateOf(1)
    var totalPage = mutableStateOf(0)
    var endReached = mutableStateOf(false)
    var createPdfStatus = mutableStateOf<Boolean?>(null)


    var orderUUID: String = ""


    init {
        viewModelScope.launch {

            testingWithDelay()




        }
    }

    private fun testingWithDelay(){
        viewModelScope.launch {
            delay(10000)
            Log.d("listOfChartItem", listOfChartItem.value.toString())
            Log.d("listOfOrder", listOfOrder.value.toString())
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
                            response.data!!.access_token
                        )
                        apply()
                    }
                    accessToken.value = response.data!!.access_token
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

    fun filterMenuToBeEdited(uuid: String) {
        val menuEdit = listOfMenu.value.first { menuItem ->
            menuItem.menuUuid == uuid
        }

        editMenu.value = menuEdit
    }


    fun getInvoicesDetail(noInvoices: String, accessToken: String) {
        viewModelScope.launch(dispatchers.io) {
            val response =
                orderRepository.getNoInvoices(accessToken = accessToken, noInvoices = noInvoices)

            when (response) {
                is Resource.Success -> {

                    invoicesDetail.value = response.data


                }

                is Resource.Error -> {

                }
            }
        }
    }


    fun getAllCategory(accessToken: String) {
        viewModelScope.launch(dispatchers.io) {
            val response = menuRepository.getListCategory(accessToken = accessToken)

            when (response) {
                is Resource.Success -> {

                    listOfCategory.value = response.data!!.data


                }

                is Resource.Error -> {
                    networkCategoryError.value = true
                    networkCategoryErrorMessage.value = response.message.toString()
                }
            }
        }
    }

    fun getChartData(accessToken: String){
        viewModelScope.launch(dispatchers.io) {
            val response = invoicesRepository.getChartData(accessToken = accessToken)

            when (response) {
                is Resource.Success -> {

                    listOfChartItem.value = response.data!!.listChartData


                }


            }
        }
    }

    fun getInvoicesList(accessToken: String) {
        viewModelScope.launch(dispatchers.io) {
            val response = invoicesRepository.getListInvoices(accessToken = accessToken)

            when (response) {
                is Resource.Success -> {

                    listOfInvoices.value = response.data!!.invoicesItem
                    invoicesResponse.value = response.data

                }

                is Resource.Error -> {
                    networkInvoicesError.value = true
                    networkInvoicesErrorMessage.value = response.message.toString()

                }
            }
        }
    }

    fun getAnotherInvoicesList(accessToken: String, page:Int) {
        viewModelScope.launch(dispatchers.io) {
            val response = invoicesRepository.getAnotherListInvoices(accessToken = accessToken, page = page)

            when (response) {
                is Resource.Success -> {
                    endReached.value = response.data!!.lastPage <= response.data.page.toInt()
                    if (response.data.invoicesItem.isEmpty()){
                        listOfInvoices.value = listOfInvoices.value
                        endReached.value = true
                    }
                    if (response.data.invoicesItem.isNotEmpty()){
                        listOfInvoices.value = newAddedToList(list = response.data.invoicesItem,oldItem = listOfInvoices.value)
                    }


                }

                is Resource.Error -> {
                    networkInvoicesError.value = true
                    networkInvoicesErrorMessage.value = response.message.toString()

                }
            }
        }
    }


    fun newOrder(singleOrderRequest: SingleOrderRequest, accessToken: String) {
        viewModelScope.launch(dispatchers.io) {
            val response = orderRepository.newOrder(
                accessToken = accessToken,
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
        var allResponse: List<OrderData> = listOf()
        var lastPage = 0
        var currentPage = 1

        viewModelScope.launch(dispatchers.io) {
            val firstResponse =
                orderRepository.getListOrder(accessToken = accessToken, page = 1).data
            if (firstResponse != null) {
                lastPage = firstResponse.totalPage
            }
            while (currentPage <= lastPage) {
                val response =
                    orderRepository.getListOrder(accessToken = accessToken, page = currentPage).data
                allResponse += response!!.orderData
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

    fun filterOrder(){

    }


    fun getSingleOrder(accessToken: String, orderUUID: String) {
        Log.d("getSingleCartInvoked", "getSingleCartInvoked")
        viewModelScope.launch(dispatchers.io) {
            val response =
                orderRepository.getOrder(accessToken = accessToken, orderUUID = orderUUID)
            when (response) {
                is Resource.Success -> {

                    singleOrderResponse.value = response.data!!
                    singleListOfCarts.value = response.data.menus
                    Log.d("succesLoadMenu", singleListOfCarts.value.toString())


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
                    listOfMenu.value = response.data!!.menuData

                }

                is Resource.Error -> {

                    networkOrderErrorMessage.value = response.message.toString()

                }
            }
        }
    }


    //TODO Create Pdf Function //
    fun createPdf(application: Context, pdfObject: PDFObject) {
        viewModelScope.launch(dispatchers.default) {

            val bitmap = BitmapFactory.decodeResource(
                application.resources,
                R.drawable.circlemangomase
            )
            val height = 100
            val width = 100


            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, height, width, true)

            val fileManager = FileManager.getInstance()
            fileManager.cleanTempFolder(application)

            // Inisiasi View
            val view: MutableList<View> = arrayListOf()
            // Membuat Layout Utama PDF
            val verticalPdf = PDFVerticalView(application)
            // Setting Layout PDF
            verticalPdf.setLayout(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )

            // Divider
            val separator1 = PDFLineSeparatorView(application)
            val separator2 = PDFLineSeparatorView(application)
            val separator3 = PDFLineSeparatorView(application)
            separator1.setBackgroundColor(Color.BLACK)
            separator2.setBackgroundColor(Color.BLACK)
            separator3.setBackgroundColor(Color.BLACK)

            val imageHorizontalView = PDFHorizontalView(application)
            val customerHorizontalView = PDFHorizontalView(application)
            val totalPriceHorizontalView = PDFHorizontalView(application)
            val discountHorizontalView = PDFHorizontalView(application)
            val totalPriceAfterDiscountHorizontalView = PDFHorizontalView(application)
            val totalSmallPriceAfterDiscountHorizontalView = PDFHorizontalView(application)
            val customerCash = PDFHorizontalView(application)
            val customerChange= PDFHorizontalView(application)




            verticalPdf.setPadding(20, 10, 20, 10)
            val customerLayoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)
            customerLayoutParams.setMargins(0, 0, 0, 0)
            customerHorizontalView.setLayout(
                customerLayoutParams
            )

            // Invoices
            val noInvoices = PDFTextView(
                application,
                PDFTextView.PDF_TEXT_SIZE.P
            ).setText("Invoice No. ${pdfObject.noInvoice}")
            // Invoices Layout
            noInvoices.view.setPadding(0, 20, 0, 10)
            noInvoices.view.gravity = Gravity.CENTER_HORIZONTAL

            // Logo
            val image = PDFImageView(application).setImageBitmap(scaledBitmap)
            imageHorizontalView.addView(image)
            imageHorizontalView.view.gravity = Gravity.CENTER_HORIZONTAL


            // Customer Name
            val customerAttribute =
                PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText("Customer Name")
            val customerName =
                PDFTextView(
                    application,
                    PDFTextView.PDF_TEXT_SIZE.P
                ).setText(pdfObject.customerName)


            // Customer Layout
            customerAttribute.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )
            customerName.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )
            customerHorizontalView.addView(customerAttribute)
            customerHorizontalView.addView(customerName)

            customerAttribute.view.gravity = Gravity.START
            customerName.view.gravity = Gravity.END

            // List dari cart objek
            val list = pdfObject.listOfCarts.map { cart ->
                cartListing(application = application, Cart = cart)
            }


            // Total Harga Dan Quantity

            totalPriceHorizontalView.setLayout(
                customerLayoutParams
            )

            totalSmallPriceAfterDiscountHorizontalView.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )
            totalSmallPriceAfterDiscountHorizontalView.view.gravity = Gravity.CENTER_HORIZONTAL


            val totalItem =
                PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText("Total Item")
            val quantity =
                PDFTextView(
                    application,
                    PDFTextView.PDF_TEXT_SIZE.P
                ).setText(pdfObject.listOfCarts.map { cart ->
                    cart.quantity
                }.sum().toString())
            val totalHarga =
                PDFTextView(
                    application,
                    PDFTextView.PDF_TEXT_SIZE.P
                ).setText((pdfObject.sumTotal.toInt() - pdfObject.discount.toInt()).toString())

            totalPriceHorizontalView.addView(totalItem)
            totalSmallPriceAfterDiscountHorizontalView.addView(quantity)
            totalSmallPriceAfterDiscountHorizontalView.addView(totalHarga)
            totalPriceHorizontalView.addView(totalSmallPriceAfterDiscountHorizontalView)

            totalItem.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            ).view.gravity = Gravity.START
            quantity.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            ).view.gravity = Gravity.END
            totalHarga.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            ).view.gravity = Gravity.END

            // Discount
            discountHorizontalView.setLayout(
                customerLayoutParams
            )
            val discount =
                PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText("Total Disc")
            val discountTotal =
                PDFTextView(
                    application,
                    PDFTextView.PDF_TEXT_SIZE.P
                ).setText(pdfObject.discount)

            discount.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )
            discountTotal.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )

            discount.view.gravity = Gravity.START
            discountTotal.view.gravity = Gravity.END

            discountHorizontalView.addView(discount)
            discountHorizontalView.addView(discountTotal)


            // Total price
            totalPriceAfterDiscountHorizontalView.setLayout(
                customerLayoutParams
            )
            val totalPriceName =
                PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText("Total Belanja")
            val totalPriceAmount =
                PDFTextView(
                    application,
                    PDFTextView.PDF_TEXT_SIZE.P
                ).setText(pdfObject.sumTotal)

            totalPriceName.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )
            totalPriceAmount.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )

            totalPriceName.view.gravity = Gravity.START
            totalPriceAmount.view.gravity = Gravity.END

            totalPriceAfterDiscountHorizontalView.addView(totalPriceName)
            totalPriceAfterDiscountHorizontalView.addView(totalPriceAmount)

            // Customer Cash
            customerCash.setLayout(
                customerLayoutParams
            )
            val customerCashAttrb =
                PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText("Cash")
            val customerCashAmount =
                PDFTextView(
                    application,
                    PDFTextView.PDF_TEXT_SIZE.P
                ).setText(pdfObject.sumTotal)

            customerCashAttrb.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )
            customerCashAmount.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )

            customerCashAttrb.view.gravity = Gravity.START
            customerCashAmount.view.gravity = Gravity.END

            customerCash.addView(customerCashAttrb)
            customerCash.addView(customerCashAmount)

            // Customer Cash
            customerChange.setLayout(
                customerLayoutParams
            )
            val customerChangeAttrb =
                PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText("Change")
            val customerChangeAmount =
                PDFTextView(
                    application,
                    PDFTextView.PDF_TEXT_SIZE.P
                ).setText(pdfObject.sumTotal)

            customerChangeAttrb.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )
            customerChangeAmount.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )

            customerChangeAttrb.view.gravity = Gravity.START
            customerChangeAmount.view.gravity = Gravity.END

            customerChange.addView(customerChangeAttrb)
            customerChange.addView(customerChangeAmount)





            verticalPdf.addView(noInvoices)
            verticalPdf.addView(imageHorizontalView)
            verticalPdf.addView(separator1)
            verticalPdf.addView(customerHorizontalView)
            verticalPdf.addView(separator2)
            list.forEach { pdfView ->
                verticalPdf.addView(pdfView)
            }
            verticalPdf.addView(separator3)
            verticalPdf.addView(totalPriceHorizontalView)
            verticalPdf.addView(discountHorizontalView)
            verticalPdf.addView(totalPriceAfterDiscountHorizontalView)
            verticalPdf.addView(customerCash)
            verticalPdf.addView(customerChange)


            val verticalPdfView = verticalPdf.view
            view.add(verticalPdfView)


            val filename = "invoices"



            try {

                PDFUtil.getInstance().generatePDF(
                    view, fileManager.createTempFileWithName(
                        application,
                        "$filename.pdf", false
                    ).absolutePath, object : PDFUtil.PDFUtilListener {
                        override fun pdfGenerationSuccess(savedPDFFile: File?) {

                            createPdfStatus.value = true

                        }

                        override fun pdfGenerationFailure(exception: Exception?) {

                            createPdfStatus.value = false

                        }
                    })

            } catch (e: Error) {
                createPdfStatus.value = false
                Log.d("IOException", e.toString())

            }
        }
    }

    fun cartListing(application: Context, Cart: Cart): PDFHorizontalView {
        val cartHorizontalView = PDFHorizontalView(application)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)
        layoutParams.setMargins(0, 0, 0, 0)
        cartHorizontalView.setLayout(
            layoutParams
        )


        val cartName =
            PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText(Cart.menuName)
        val cartQuantity =
            PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText(Cart.quantity.toString())
        val cartPrice =
            PDFTextView(application, PDFTextView.PDF_TEXT_SIZE.P).setText(Cart.price)
        val cartTotalPrice =
            PDFTextView(
                application,
                PDFTextView.PDF_TEXT_SIZE.P
            ).setText((Cart.price.toInt() * Cart.quantity).toString())

        cartName.setLayout(
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
        )
        cartQuantity.setLayout(
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
        )
        cartPrice.setLayout(
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
        )
        cartTotalPrice.setLayout(
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
        )
        cartName.view.gravity = Gravity.START
        cartQuantity.view.gravity = Gravity.END
        cartPrice.view.gravity = Gravity.END
        cartTotalPrice.view.gravity = Gravity.END



        cartHorizontalView.addView(cartName)
        cartHorizontalView.addView(cartQuantity)
        cartHorizontalView.addView(cartPrice)
        cartHorizontalView.addView(cartTotalPrice)

        return cartHorizontalView
    }

    fun payOrder(accessToken: String, uuid: String, payRequest: PayRequest) {
        viewModelScope.launch {
            val response = orderRepository.payOrder(
                accessToken = accessToken,
                uuid = uuid,
                payRequest = payRequest
            )
            when (response) {
                is Resource.Success -> {
                    networkPayOrderError.value = true

                }

                is Resource.Error -> {
                    networkPayOrderError.value = false
                }
            }
        }
    }

    @InternalAPI
    fun createMenu(accessToken: String, menuItem: MenuItem, uri: String) {
        viewModelScope.launch(dispatchers.io) {
            val response =
                menuRepository.newMenu(accessToken = accessToken, menuItem = menuItem, uri = uri)
            when (response) {

                is Resource.Success -> {
                    createMenuStatus.value = true

                }

                is Resource.Error -> {
                    createMenuStatus.value = false


                }
            }

        }
    }

    @InternalAPI
    fun updateMenu(accessToken: String, menuItem: MenuItem, uri: String) {
        viewModelScope.launch(dispatchers.io) {
            val response =
                menuRepository.updateMenu(accessToken = accessToken, menuItem = menuItem, uri = uri)
            when (response) {

                is Resource.Success -> {
                    updateMenuStatus.value = true

                }

                is Resource.Error -> {
                    updateMenuStatus.value = false


                }
            }

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