package com.example.mangopos.presentation.ui.screen.transactionscreen

import android.Manifest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.data.objects.dto.OrderData
import com.example.mangopos.data.objects.dto.OrderItem
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.component.ListComponentTransaction
import com.example.mangopos.presentation.component.TransactionHeaderList
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@Stable
@ExperimentalMaterialApi
@Composable
fun TransactionScreen(
    mainViewModel: MainViewModel,
    scaffoldState: ScaffoldState,
    drawerState: BottomDrawerState,
    navController: NavController,
    listOfOrder: List<OrderData>
) {

    val coroutineScope = rememberCoroutineScope()


    val accessToken by remember { mainViewModel.accessToken }
    val networkErrorMessage by remember { mainViewModel.networkOrderErrorMessage }


    val endReached by remember { mainViewModel.endReached }
    val error by remember { mainViewModel.networkErrorOrder }

    val totalPage by remember { mainViewModel.totalPage }
    val permission =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    var loading = true


    Log.d("endReached", endReached.toString())


    if (listOfOrder.isEmpty()) {
        loading = false
    }
    if (listOfOrder.isNotEmpty()) {
        loading = false
    }

    LaunchedEffect(key1 = true) {
        mainViewModel.networkPayOrderError.value = null
        mainViewModel.getAllCategory(accessToken = accessToken)
        mainViewModel.getMenuList(accessToken = accessToken)
        mainViewModel.newOrderStatus.value = null
        mainViewModel.listOfCartNewOrder.value = listOf()

    }

    PermissionRequired(
        permissionState = permission,
        permissionNotGrantedContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Grant The Permission")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { permission.launchPermissionRequest() }) {
                        Text(text = "Ok!")
                    }
                    Button(onClick = { doNotShowRationale = true }) {
                        Text(text = "Nope")
                    }
                }
            }
        },
        permissionNotAvailableContent = {
            Text("Permission denied")
        }) {
        val isRefreshing by mainViewModel.isRefresing.collectAsState()

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing == true),
            onRefresh = { mainViewModel.getAllOrder(accessToken = accessToken) }) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                if (scaffoldState.snackbarHostState.currentSnackbarData?.message == "Login Success") {
                    scaffoldState.snackbarHostState.currentSnackbarData!!.dismiss()
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TransactionHeaderList()
                    if (error == true) {

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            elevation = 4.dp,
                            color = Color.White,
                            modifier = Modifier
                                .width(800.dp)
                                .height(250.dp)
                                .padding(3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Transparent),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = networkErrorMessage)

                            }

                        }
                    }
                    Surface(
                        color = Color.Transparent,
                        modifier = Modifier
                            .requiredHeight(350.dp)
                            .width(800.dp)
                    ) {

                        if (listOfOrder.isEmpty() && error == null) {
                            Box(
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                if (loading) {
                                    CircularProgressIndicator()
                                }
                                if (!loading) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "Order List Is Empty")
                                    }
                                }

                            }

                        }
                        if (listOfOrder.isNotEmpty()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ListComponentTransaction(
                                    data = listOfOrder,
                                    mainViewModel = mainViewModel,
                                    drawerState = drawerState,
                                    navController = navController,
                                    accessToken = accessToken
                                )
                            }
                        }


                    }
                }

            }
        }

    }


}