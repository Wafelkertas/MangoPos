package com.example.mangopos.presentation.ui.screen.invoicesscreen

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mangopos.data.dummyData
import com.example.mangopos.presentation.component.InvoicesHeaderList
import com.example.mangopos.presentation.component.ListComponentInvoices
import com.example.mangopos.presentation.ui.theme.fff6c2


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun InvoicesScreen() {
    val data by remember { mutableStateOf(dummyData) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(fff6c2),
        contentAlignment = Alignment.Center
    ) {

        Column {
            Spacer(modifier = Modifier.height(10.dp))
            InvoicesHeaderList()
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .requiredHeight(400.dp)
                    .width(800.dp)
            ) {
                Column {

                    Spacer(modifier = Modifier.height(5.dp))
                    ListComponentInvoices(data)
                }
            }
        }
    }


}
