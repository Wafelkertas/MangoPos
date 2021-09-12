package com.example.mangopos.presentation.ui.screen.orderscreen


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mangopos.data.objects.dto.Cart
import com.example.mangopos.data.objects.dto.MenuItem
import com.example.mangopos.data.objects.dto.SingleOrderRequest
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.component.MenuGrid
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.presentation.ui.screen.ediorderscreen.requestListCart
import com.example.mangopos.presentation.ui.theme.cff6c2
import com.example.mangopos.utils.addItemToList
import com.example.mangopos.utils.deleteItemFromList
import com.example.mangopos.utils.removeItemToList
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun OrderScreen(
    mainViewModel: MainViewModel,
    coroutineScope: CoroutineScope,
    drawerState: BottomDrawerState,
    menuItemList: List<MenuItem>,
    accessToken: String
) {
    var customerName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {

        val navHost = rememberAnimatedNavController()

        AnimatedNavHost(navController = navHost, startDestination = Screen.Order.route) {
            composable(route = Screen.Order.route,
                exitTransition = { _, _ ->
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth / 5 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                },
                enterTransition = { _, _ ->
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth / 5 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                popEnterTransition = { _, _ ->
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth / 5 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                }
            ) {
                NewOrder(
                    drawerState = drawerState,
                    navController = navHost,
                    customerName = customerName,
                    onValueChange = { customerName = it },
                    onCancelOrder = { customerName = "" }
                )
            }
            composable(Screen.OrderMenu.route,
                exitTransition = { _, _ ->
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth / 5 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                },
                enterTransition = { _, _ ->
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth / 5 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                popEnterTransition = { _, _ ->
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth / 5 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                }
            ) {
                MenuOrderScreen(
                    drawerState = drawerState,
                    menuItemList = menuItemList,
                    customerName = customerName,
                    navController = navHost,
                    onCancelOrder = { customerName = "" },
                    accessToken = accessToken
                )
            }
        }


    }

}

@ExperimentalMaterialApi
@Composable
fun NewOrder(
    drawerState: BottomDrawerState,
    navController: NavController,
    customerName: String,
    onValueChange: (String) -> Unit,
    onCancelOrder: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
//    var customerName by remember { mutableStateOf("") }
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.5f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(0.5f),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "New Order",
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )

                OutlinedTextField(
                    value = customerName,
                    onValueChange = onValueChange,
                    label = {
                        Text(
                            text = "Input Customer Name"
                        )
                    })

                Row(
                    horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(5.dp)
                ) {
                    Button(onClick = {
                        onCancelOrder.invoke()
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }) {
                        Text(text = "Cancel")
                    }
                    Button(enabled = customerName.isNotBlank(),onClick = {
                        navController.navigate(Screen.OrderMenu.route) {
                            popUpTo(Screen.OrderMenu.route) {
                                inclusive = true

                            }

                        }
                    }) {
                        Text(text = "Order")
                    }
                }
            }

        }

    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MenuOrderScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    drawerState: BottomDrawerState,
    menuItemList: List<MenuItem>,
    customerName : String,
    onCancelOrder: () -> Unit,
    accessToken : String
) {


    val listOfCartNewOrder by remember { mainViewModel.listOfCartNewOrder }
    val status by remember { mainViewModel.newOrderStatus }
//    val accessToken by remember { mainViewModel.accessToken }
    val coroutineScope = rememberCoroutineScope()

    Log.d("orderscreen", accessToken)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        if (status == true) {
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


                    Text(
                        text = "New Order", textAlign = TextAlign.Start, modifier = Modifier
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
                        cartUuid = "",
                        mainViewModel = mainViewModel,
                        inEditOrder = false
                    )

                    Text(
                        text = "Customer Name : $customerName",
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, top = 10.dp)
                    )

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

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            itemsIndexed(listOfCartNewOrder) { index, data ->

                                var unread by remember { mutableStateOf(false) }
                                val dismissState = rememberDismissState(
                                    confirmStateChange = {
                                        if (it == DismissValue.DismissedToEnd) {
                                            unread = !unread

                                            mainViewModel.singleListOfCarts.value =
                                                deleteItemFromList(
                                                    mainViewModel.singleListOfCarts.value,
                                                    data
                                                )


                                        }
                                        it != DismissValue.DismissedToEnd
                                    }
                                )
                                if (data != null) {
//                                        CartItem(carts = data, index = index, data.quantity)
                                    SwipeToDismissOrder(
                                        dismissState = dismissState,
                                        cart = data,
                                        unread = unread,
                                        index = index,
                                        incrementEvent = {
                                            mainViewModel.listOfCartNewOrder.value =
                                                addItemToList(
                                                    mainViewModel.listOfCartNewOrder.value,
                                                    data
                                                )
                                        },
                                        decrementEvent = {
                                            mainViewModel.listOfCartNewOrder.value =
                                                removeItemToList(
                                                    mainViewModel.listOfCartNewOrder.value,
                                                    data
                                                )
                                        }
                                    )
                                }
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
                            onCancelOrder.invoke()
                            navController.popBackStack()
                            mainViewModel.listOfCartNewOrder.value = listOf()

                        }) {
                            Text(text = "Cancel")
                        }
                        Button(enabled = listOfCartNewOrder.isNotEmpty(),onClick = {
                            coroutineScope.launch {
                                if (listOfCartNewOrder.isNotEmpty()) {
                                    Log.d("orderscreen", accessToken)

                                    mainViewModel.newOrder(
                                        singleOrderRequest = SingleOrderRequest(
                                            carts = requestListCart(singleListOfCarts = listOfCartNewOrder),
                                            customerName = customerName,
                                        ),
                                        accessToken = accessToken

                                    )


                                }


                            }
                        }) {
                            Text(text = "Submit Order")
                        }
                    }

                }

            }
        }

    }


}


@ExperimentalMaterialApi
@Composable
fun SwipeToDismissOrder(
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
        directions = setOf(DismissDirection.StartToEnd),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
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