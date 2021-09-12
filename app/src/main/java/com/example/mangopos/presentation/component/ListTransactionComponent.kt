package com.example.mangopos.presentation.component

import android.util.Log
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.data.objects.dto.OrderItem
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.ui.theme.ffdd49
import com.example.mangopos.presentation.ui.theme.fff6c2
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListComponentTransaction(
    navController: NavController,
    data: List<OrderItem?>,
    mainViewModel: MainViewModel,
    drawerState: BottomDrawerState,
    accessToken: String
) {

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        itemsIndexed(items = data) { index, data ->

            TransactionItem(
                data = data,
                index = index,
                mainViewModel = mainViewModel,
                drawerState = drawerState,
                navController = navController
            )

        }
    }


}

@ExperimentalMaterialApi
@Composable
fun TransactionItem(
    navController: NavController,
    data: OrderItem?,
    mainViewModel: MainViewModel,
    drawerState: BottomDrawerState,
    index: Int
) {
    val coroutineScope = rememberCoroutineScope()
    val accessToken by remember { mainViewModel.accessToken }


    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        color = fff6c2,
        modifier = Modifier
            .width(800.dp)
            .height(75.dp)
            .padding(3.dp)
    ) {

        if (data != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .fillMaxHeight()
                    .background(Color.Transparent)
                    .clickable {
                        mainViewModel.editOrder.value = false

                        Log.d("mainViewModelDataUUID", data.uuid)
                        mainViewModel.orderUUID = data.uuid

                        mainViewModel.getSingleOrder(
                            accessToken = accessToken,
                            orderUUID = data.uuid
                        )
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }

            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = (index + 1).toString(),
                    modifier = Modifier
                        .weight(0.25f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = data.customerName,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(0.25f)
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "${data.createdAt.dropLast(17)}  ${
                        data.createdAt.dropLast(7).drop(11)
                    }  ",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(0.25f)
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "${data.updatedAt.dropLast(17)}  ${
                        data.updatedAt.dropLast(7).drop(11)
                    } ",
                    modifier = Modifier
                        .weight(0.25f)
                        .align(Alignment.CenterVertically)
                )


            }
        }
    }


}

@Composable
fun TransactionHeaderList() {

    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        color = Color.Transparent,
        modifier = Modifier
            .width(800.dp)
            .height(75.dp)
            .padding(3.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .background(Brush.verticalGradient(listOf(ffdd49, fff6c2), startY = 0.8f))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(3.dp)
                .background(Color.Transparent)
        ) {

            Text(
                textAlign = TextAlign.Center,
                text = "No",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.25f)
            )
            Text(
                textAlign = TextAlign.Center,
                text = " Name",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.25f)

            )

            Text(
                textAlign = TextAlign.Center,
                text = "Tanggal Order",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.25f)
            )

            Text(
                textAlign = TextAlign.Center,
                text = "Tanggal Bayar",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.25f)
            )


        }

    }
}