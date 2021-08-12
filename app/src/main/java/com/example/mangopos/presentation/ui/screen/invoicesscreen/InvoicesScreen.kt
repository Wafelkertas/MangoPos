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
import com.example.mangopos.presentation.component.ListComponent

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun InvoicesScreen() {
    val data by remember { mutableStateOf(dummyData) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(5.dp))
                .requiredHeight(400.dp)
                .width(800.dp)
                .background(Color.White)
        ) {
            Column {

            Spacer(modifier = Modifier.height(20.dp))
            ListComponent(data)
            }
        }
    }


}
