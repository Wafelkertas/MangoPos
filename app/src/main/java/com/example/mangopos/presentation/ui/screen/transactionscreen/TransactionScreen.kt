package com.example.mangopos.presentation.ui.screen.transactionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mangopos.data.dummyData
import com.example.mangopos.presentation.component.ListComponentTransaction
import com.example.mangopos.presentation.ui.theme.a67d00
import com.example.mangopos.presentation.ui.theme.fff6c2

@ExperimentalMaterialApi
@Composable
fun TransactionScreen() {
    val data by remember { mutableStateOf(dummyData) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {


            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(20.dp))
                ListComponentTransaction(data)
            }


    }
}