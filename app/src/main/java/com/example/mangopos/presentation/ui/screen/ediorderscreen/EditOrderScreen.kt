package com.example.mangopos.presentation.ui.screen.ediorderscreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.DismissValue.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.data.objects.dto.Cart
import com.example.mangopos.data.objects.dto.MenuItem
import com.example.mangopos.data.objects.dto.SingleOrderRequest
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.component.MenuGrid
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.presentation.ui.theme.cff6c2
import com.example.mangopos.utils.addItemToList
import com.example.mangopos.utils.deleteItemFromList
import com.example.mangopos.utils.removeItemToList
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun EditOrderScreen(
    mainViewModel: MainViewModel,
    drawerState: BottomDrawerState,
    menuItemList: List<MenuItem>,
    navController: NavController
) {


    val singleListOfCarts by remember { mainViewModel.singleListOfCarts }
    val singleOrderResponse by remember { mainViewModel.singleOrderResponse }
    val networkSingleCartErrorMessage by remember { mainViewModel.networkSingleOrderErrorMessage }
    val networkSingleCartError by remember { mainViewModel.networkSingleOrderError }
    val updateOrderStatus by remember { mainViewModel.updateOrderStatus }
    val accessToken by remember { mainViewModel.accessToken }

    var loading by remember { mutableStateOf<Boolean?>(null) }


    Log.d("singleListOfCarts", singleListOfCarts.toString())


    val uuid = mainViewModel.orderUUID

    if (updateOrderStatus == true) {
        LaunchedEffect(key1 = true) {
            navController.navigate(Screen.Transaction.route)

        }
    }


    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {

        if (networkSingleCartError == true) {
            coroutineScope.launch {

                drawerState.close()
            }
        }

        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
        ) {

            Surface(
                elevation = 3.dp,
                shape = RoundedCornerShape(5.dp),
                color = Color.White,
                modifier = Modifier.fillMaxSize(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxSize()
                ) {
                    if (singleOrderResponse != null) {

                        Text(
                            text = "Edit Order", textAlign = TextAlign.Start, modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Menu", textAlign = TextAlign.Start, modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp)
                        )

                        MenuGrid(
                            listMenus = menuItemList,
                            cartUuid = uuid,
                            mainViewModel = mainViewModel,
                            inEditOrder = true
                        )

                        Text(
                            text = "Customer Name : ${singleOrderResponse!!.customerName}",
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, top = 10.dp)
                        )

                    }

                }


            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight()
                .weight(0.9f)
                .padding(5.dp)
        ) {
            Surface(
                elevation = 3.dp,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Cart")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "No",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                        )
                        Text(
                            text = "Menu Item",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                        )
                        Text(
                            text = "Quantity",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                        )

                    }
                    Surface(
                        color = Color.White, shape = RoundedCornerShape(5.dp), modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(0.85f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(0.5f),
                            contentAlignment = Alignment.Center
                        ) {


                            if (singleListOfCarts.isEmpty()) {

                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    loading = false
                                    Text(text = "Cart is Empty")
                                }
                            }


                        }


                        if (singleListOfCarts.isNotEmpty()) {
                            loading = false
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                itemsIndexed(singleListOfCarts) { index, data ->

                                    var unread by remember { mutableStateOf(false) }
                                    val dismissState = rememberDismissState(
                                        confirmStateChange = {
                                            if (it == DismissedToEnd) {
                                                unread = !unread

                                                mainViewModel.singleListOfCarts.value =
                                                    deleteItemFromList(
                                                        mainViewModel.singleListOfCarts.value,
                                                        data
                                                    )


                                            }
                                            it != DismissedToEnd
                                        }
                                    )
                                    if (data != null) {
//                                        CartItem(carts = data, index = index, data.quantity)
                                        SwipeToDismiss(
                                            dismissState = dismissState,
                                            cart = data,
                                            unread = unread,
                                            index = index,
                                            incrementEvent = {
                                                mainViewModel.singleListOfCarts.value =
                                                    addItemToList(
                                                        mainViewModel.singleListOfCarts.value,
                                                        data
                                                    )
                                            },
                                            decrementEvent = {
                                                mainViewModel.singleListOfCarts.value =
                                                    removeItemToList(
                                                        mainViewModel.singleListOfCarts.value,
                                                        data
                                                    )
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        if (networkSingleCartError == true) {
                            Box(
                                modifier = Modifier.fillMaxSize(0.5f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = networkSingleCartErrorMessage)
                            }
                        }


                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            coroutineScope.launch { drawerState.close() }
                        }) {

                            Text(text = "Cancel")
                        }
                        Button(enabled = singleListOfCarts.isNotEmpty(), onClick = {
                            coroutineScope.launch {
                                if (singleOrderResponse != null) {
                                    mainViewModel.editAnOrder(
                                        singleOrderRequest = SingleOrderRequest(
                                            carts = requestListCart(singleListOfCarts = singleListOfCarts),
                                            customerName = singleOrderResponse!!.customerName,


                                            ), orderUUID = singleOrderResponse!!.uuid

                                    )

                                }


                            }
                        }) {
                            Text(text = "Submit Order")
                        }
                        Button(onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate(Screen.CheckOutScreen.route)
                            }
                        }) {
                            Text(text = "Checkout Order")
                        }
                    }

                }
            }

        }


    }
}

fun requestListCart(singleListOfCarts: List<Cart>): List<Cart> {
    var newList = listOf<Cart>()
    if (singleListOfCarts.isEmpty()) {
        newList = listOf()
    }
    if (singleListOfCarts.isNotEmpty()) {
        newList = singleListOfCarts
    }
    return newList
}


@ExperimentalMaterialApi
@Composable
fun SwipeToDismiss(
    dismissState: DismissState,
    cart: Cart,
    unread: Boolean,
    index: Int,
    incrementEvent: () -> Unit,
    decrementEvent: () -> Unit
) {

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 4.dp),
        directions = setOf(StartToEnd),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == StartToEnd) 0.25f else 0.5f)
        },
        background = {

        },
        dismissContent = {

            Surface(
                shape = RoundedCornerShape(5.dp),
                elevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(5.dp),
                color = cff6c2
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = index.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = cart.menuName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = incrementEvent, modifier = Modifier.weight(1f)) {
                            Icon(
                                tint = MaterialTheme.colors.primary,
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = ""
                            )
                        }
                        Text(
                            text = cart.quantity.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                                .background(Color.White)
                                .clip(RoundedCornerShape(10.dp))

                        )
                        IconButton(
                            onClick = decrementEvent,
                            modifier = Modifier.weight(1f),
                            enabled = cart.quantity > 0
                        ) {

                            Icon(
                                tint = MaterialTheme.colors.primary,
                                imageVector = Icons.Default.RemoveCircle,
                                contentDescription = ""
                            )

                        }
                    }
                }
            }
        })
}







