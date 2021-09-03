package com.example.mangopos.presentation.ui.screen.transactionscreen

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.data.objects.dto.OrderItem
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.component.ListComponentTransaction
import com.example.mangopos.presentation.component.TransactionHeaderList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
@ExperimentalMaterialApi
@Composable
fun TransactionScreen(
    mainViewModel: MainViewModel,
    scaffoldState: ScaffoldState,
    drawerState: BottomDrawerState,
    navController: NavController,
    listOfOrder: List<OrderItem>
) {

    val coroutineScope = rememberCoroutineScope()


    val accessToken by remember { mainViewModel.accessToken }
    val networkErrorMessage by remember { mainViewModel.networkOrderErrorMessage }
    val orderResponse by remember { mainViewModel.orderResponse }
    val endReached by remember { mainViewModel.endReached }
    val error by remember { mainViewModel.networkErrorOrder }
    val currentPage by remember { mainViewModel.currentPage }
    val totalPage by remember { mainViewModel.totalPage }



    Log.d("endReached", endReached.toString())






    LaunchedEffect(key1 = true ) {
        mainViewModel.getAllCategory(accessToken = accessToken)
        mainViewModel.getMenuList(accessToken = accessToken)
    }




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

                        CircularProgressIndicator()
                    }

                }
                if (listOfOrder.isNotEmpty()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ListComponentTransaction(
                            data = listOfOrder,
                            mainViewModel = mainViewModel,
                            drawerState = drawerState,
                            navController = navController
                        )
                    }
                }


            }
        }

    }


}