package com.example.mangopos.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mangopos.data.objects.dto.InvoicesItem


import com.example.mangopos.presentation.ui.theme.ffdd49
import com.example.mangopos.presentation.ui.theme.fff6c2


/*
Component for Transaction
* */





@Composable
fun ListComponentInvoices(
    data: List<InvoicesItem>
) {

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        itemsIndexed(items = data) { index, data ->

            InvoicesItem(
                data = data,
                index = index
            )

        }
    }
}


@Composable
fun InvoicesItem(data: InvoicesItem, index: Int) {


    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        color = ffdd49,
        modifier = Modifier
            .width(800.dp)
            .height(75.dp)
            .padding(5.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
        ) {

            Text(
                textAlign = TextAlign.Center,
                text = data.name,
                modifier = Modifier
                    .weight(0.2f)
                    .align(CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = data.quantity,
                modifier = Modifier
                    .weight(0.2f)
                    .align(CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = data.cash,
                modifier = Modifier
                    .weight(0.2f)
                    .align(CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "${data.createdAt.dropLast(17)}  ${
                    data.createdAt.dropLast(7).drop(11)
                }  ",
                modifier = Modifier
                    .weight(0.2f)
                    .align(CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = data.totalPrice,
                modifier = Modifier
                    .weight(0.2f)
                    .align(CenterVertically)
            )


        }


    }


}

@Composable
fun InvoicesHeaderList() {


    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        color = ffdd49,
        modifier = Modifier
            .width(800.dp)
            .height(75.dp)
            .padding(5.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .background(Brush.verticalGradient(listOf(fff6c2, ffdd49)))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(3.dp)
                .background(Color.Transparent)
        ) {

            Text(
                textAlign = TextAlign.Center,
                text = "Nama",
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Quantity",
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Cash",
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Tanggal Order",
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Total Harga",
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
            )


        }


    }


}