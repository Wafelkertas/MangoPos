package com.example.mangopos.presentation.component


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.data.objects.dto.Cart
import com.example.mangopos.data.objects.dto.MenuItem
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.utils.addItemToList

import com.example.mangopos.utils.menuToCartItem
import com.google.accompanist.coil.rememberCoilPainter
import io.ktor.util.reflect.*


@Composable
fun ItemCard(data: MenuItem, mainViewModel: MainViewModel, cartUuid: String, inEditOrder: Boolean) {


    Surface(
        shape = RoundedCornerShape(5.dp),
        color = Color.White,
        elevation = 3.dp,
        modifier = Modifier
            .width(120.dp)
            .height(200.dp)
            .padding(10.dp)
            .clickable {

                val cart = menuToCartItem(menu = data, cartUuid = cartUuid)

                if (inEditOrder) {

                    val findCart = mainViewModel.singleListOfCarts.value.find { theCart ->
                        theCart.menuUuid == data.menuUuid
                    }


                    if (findCart == null) {
                        mainViewModel.singleListOfCarts.value += cart
                    }
                    if (findCart != null) {
                        mainViewModel.singleListOfCarts.value = addItemToList(
                            list = mainViewModel.singleListOfCarts.value,
                            oldItem = findCart
                        )

                    }
                }
                if (!inEditOrder) {
                    val findCart = mainViewModel.listOfCartNewOrder.value.find { theCart ->
                        theCart.menuUuid == data.menuUuid
                    }


                    if (findCart == null) {
                        mainViewModel.listOfCartNewOrder.value += cart
                    }
                    if (findCart != null) {
                        mainViewModel.listOfCartNewOrder.value = addItemToList(
                            list = mainViewModel.listOfCartNewOrder.value,
                            oldItem = findCart
                        )

                    }
                }

            }

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(120.dp)
                .height(200.dp)
        ) {
            val painter = rememberCoilPainter(request = data.image)
            Image(
                painter = painter,
                contentDescription = "makanan",
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = data.name,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 5.dp)
            )
            Text(
                text = data.price.toString(),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 5.dp)
            )

        }

    }


}

@ExperimentalFoundationApi
@OptIn
@Composable
fun MenuGrid(listMenus: List<MenuItem>, mainViewModel: MainViewModel, cartUuid: String, inEditOrder :Boolean) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp), modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
    ) {
        items(items = listMenus) { menu ->
            ItemCard(data = menu, cartUuid = cartUuid, mainViewModel = mainViewModel, inEditOrder = inEditOrder )
        }
    }
}

@Composable
fun MenuEditGrid(data: MenuItem, mainViewModel: MainViewModel, cartUuid: String, navController: NavController) {


    Surface(
        shape = RoundedCornerShape(5.dp),
        color = Color.White,
        elevation = 3.dp,
        modifier = Modifier
            .width(120.dp)
            .height(200.dp)
            .padding(10.dp)
            .clickable {
                navController.navigate("EditMenu/${data.menuUuid}")

            }

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(120.dp)
                .height(200.dp)
        ) {
            val painter = rememberCoilPainter(request = data.image)
            Image(
                painter = painter,
                contentDescription = "makanan",
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = data.name,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 5.dp)
            )

            Text(
                text = data.price.toString(),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 5.dp)
            )

        }

    }


}

@ExperimentalFoundationApi
@OptIn
@Composable
fun EditMenuGrid(listMenus: List<MenuItem>, mainViewModel: MainViewModel, cartUuid: String, navController: NavController) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp), modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
    ) {
        items(items = listMenus) { menu ->
            MenuEditGrid(data = menu, cartUuid = cartUuid, mainViewModel = mainViewModel,navController = navController)
        }
    }
}