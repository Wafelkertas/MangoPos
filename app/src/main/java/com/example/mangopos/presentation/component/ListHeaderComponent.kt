package com.example.mangopos.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mangopos.data.DummyData
import com.example.mangopos.presentation.ui.theme.ffdd49

@Composable
fun TransactionHeaderList(data: DummyData) {



    Surface(shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        color = ffdd49,
        modifier = Modifier
            .width(800.dp)
            .height(75.dp)
            .padding(5.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .width(1000.dp)
                .fillMaxHeight()
                .padding(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)

            ) {
                Text(
                    textAlign = TextAlign.Justify,
                    text = data.name,
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = data.harga.toString(),
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = data.name,
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = data.harga.toString(),
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = data.name,
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )


            }

        }



    }


}

@Composable
fun InvoicesHeaderList() {



    Surface(shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        color = ffdd49,
        modifier = Modifier
            .width(800.dp)
            .height(75.dp)
            .padding(5.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)

            ) {
                Text(
                    textAlign = TextAlign.Justify,
                    text = "Nama",
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = "Harga",
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = "Nama",
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = "Harga",
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    textAlign = TextAlign.Justify,
                    text = "Nama",
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically)
                )


            }

        }



    }


}