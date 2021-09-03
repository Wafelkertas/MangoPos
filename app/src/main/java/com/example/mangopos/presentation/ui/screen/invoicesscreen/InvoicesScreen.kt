package com.example.mangopos.presentation.ui.screen.invoicesscreen

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangopos.presentation.MainViewModel

import com.example.mangopos.presentation.component.InvoicesHeaderList
import com.example.mangopos.presentation.component.ListComponentInvoices
import com.example.mangopos.presentation.ui.theme.fff6c2
import kotlinx.coroutines.delay


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun InvoicesScreen(mainViewModel: MainViewModel) {


    val listOfInvoice by remember { mainViewModel.listOfInvoices }
    val errorStatus by remember { mainViewModel.networkInvoicesError }
    val errorMessage by remember { mainViewModel.networkInvoicesErrorMessage }
    val accessToken by remember { mainViewModel.accessToken }


    LaunchedEffect(key1 = true) {
        mainViewModel.getInvoicesList(accessToken = accessToken)
    }




    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            InvoicesHeaderList()
            if (errorStatus == true) {
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
                        Text(text = errorMessage)

                    }

                }
            }
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .requiredHeight(350.dp)
                    .width(800.dp)
            ) {
                if (listOfInvoice.isEmpty() && errorStatus == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (listOfInvoice.isNotEmpty()) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {


                        ListComponentInvoices(data = listOfInvoice)
                    }
                }
            }
        }
    }


}
