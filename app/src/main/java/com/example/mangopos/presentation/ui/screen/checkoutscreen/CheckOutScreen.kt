package com.example.mangopos.presentation.ui.screen.checkoutscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mangopos.data.objects.dto.Cart
import com.example.mangopos.data.objects.dto.PayRequest
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.presentation.ui.theme.ffdd49
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun CheckOutScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    drawerState: BottomDrawerState
) {

    val singleOrderResponse by remember { mainViewModel.singleOrderResponse }
    val networkPayOrderError by remember { mainViewModel.networkPayOrderError }
    val accessToken by remember { mainViewModel.accessToken }
    val coroutineScope = rememberCoroutineScope()
    var totalPrice by remember { mutableStateOf(0) }
    var totalItems by remember { mutableStateOf(0) }
    var customerCash by remember { mutableStateOf("") }
    var discountString by remember { mutableStateOf("10") }
    var sumTotalPrice by remember { mutableStateOf(0) }
    var ppn by remember { mutableStateOf(0) }
    var change by remember { mutableStateOf(0) }
    var discountAmount by remember { mutableStateOf(0) }

    if (networkPayOrderError == true){
        navController.navigate(Screen.Transaction.route)
        mainViewModel.getAllCategory(accessToken = accessToken)
    }



    if (singleOrderResponse != null) {
        LaunchedEffect(key1 = true) {
            totalPrice = calculateTotalPrice(singleOrderResponse!!.carts)
            ppn = calculatePPN(totalPrice)
            totalItems = calculateItems(singleOrderResponse!!.carts)
            sumTotalPrice =
                calculateSumTotal(
                    totalPrice = totalPrice,
                    ppn = ppn,
                    discount = discountString
                )
        }


    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(ffdd49),
        contentAlignment = Alignment.Center
    ) {


            change = calculateChange(sumTotal = sumTotalPrice, cash = customerCash)

            sumTotalPrice =
                calculateSumTotal(
                    totalPrice = totalPrice,
                    ppn = ppn,
                    discount = discountString
                )
            discountAmount = calculateDiscount(
                totalPrice = totalPrice,
                ppn = ppn,
                discount = discountString.toInt()
            )




        if (singleOrderResponse != null) {


            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(15.dp),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(5.dp)

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Checkout Order ${singleOrderResponse!!.customerName}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight(2),
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "No.",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Nama Menu",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Quantitas",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Harga",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )

                            }
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f)
                                    .padding(top = 5.dp)

                            ) {

                                itemsIndexed(singleOrderResponse!!.carts) { index, item ->
                                    CheckoutItem(item = item, index = index)
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "$totalItems items",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Rp.$totalPrice",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )

                            }

                        }
                    }
                }
                Surface(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .padding(15.dp),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(5.dp)

                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "Summary", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp, start = 5.dp), fontSize = 20.sp
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {

                                Text(text = "Total Harga   ")
                                Text(text = "Rp $totalPrice ")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(text = "PPN 10%  ")
                                Text(text = "Rp $ppn ")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Rp  ")
                                OutlinedTextField(value = customerCash, onValueChange = {
                                    customerCash = it
                                }, label = { Text(text = "Input Cash") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                OutlinedTextField(
                                    value = discountString,
                                    onValueChange = {
                                        discountString = it

                                    },
                                    label = { Text(text = "Input discount") },
                                    modifier = Modifier.fillMaxWidth(0.3f),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                                Text(
                                    text = "%",
                                    modifier = Modifier.padding(start = 3.dp),
                                    fontSize = 20.sp
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(text = "Discount")
                                Text(text = "Rp $discountAmount ")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(text = "Total Tagihan")
                                Text(text = "Rp $sumTotalPrice ")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(text = "Change")
                                Text(text = "Rp $change ")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(onClick = {
                                    coroutineScope.launch {
                                        navController.navigate(Screen.Transaction.route)
                                        drawerState.open()
                                    }
                                }) {
                                    Text(text = "Cancel")
                                }
                                Button(onClick = {
                                    mainViewModel.payOrder(accessToken = accessToken, payRequest = PayRequest(
                                        cash = customerCash.toInt(),
                                        moneyChange = change.toInt(),
                                        name = singleOrderResponse!!.customerName,
                                        quantity = totalItems,
                                        totalPrice = totalPrice,
                                        total = sumTotalPrice
                                    ),
                                        uuid = singleOrderResponse!!.uuid
                                    )
                                }) {
                                    Text(text = "Pay Now")
                                }
                            }


                        }

                    }
                }

            }

        }
    }
}

fun calculateDiscount(totalPrice: Int, ppn: Int, discount: Int): Int {
    return ((totalPrice + ppn) * discount / 100)
}

fun calculateSumTotal(totalPrice: Int, discount: String, ppn: Int): Int {
    var intDiscount = 0
    if (discount.isNotEmpty()){
        intDiscount = discount.toInt()
    }
    return totalPrice + ppn - ((totalPrice + ppn) * intDiscount / 100)
}

fun calculateChange(sumTotal: Int, cash: String): Int {
    var exchange = 0
    if (cash.isNotEmpty()) {
        val newCash = cash.toInt()
        exchange = newCash - sumTotal
    }
    return exchange

}

fun calculateItems(listCart: List<Cart>): Int {
    var totalItems = 0
    for (i in listCart) {
        totalItems += i.quantity
    }
    return totalItems
}

fun calculatePPN(totalPrice: Int): Int {
    return totalPrice * 10 / 100
}

fun calculateTotalPrice(listCart: List<Cart>): Int {
    var totalPrice: Int = 0


    for (i in listCart) {
        val menuPrice = (i.price.toInt()) * (i.quantity)
        totalPrice += menuPrice
    }

    return totalPrice
}

@Composable
fun CheckoutItem(item: Cart, index: Int) {
    Column(modifier = Modifier.padding(5.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (index + 1).toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "${item.menuName}",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "${item.quantity}",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Rp.${item.price}",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }
        Divider(thickness = 2.dp)
    }
}