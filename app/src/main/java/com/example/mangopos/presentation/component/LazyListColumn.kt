package com.example.mangopos.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mangopos.data.DummyData


/*
Component for Transaction
* */
@Composable
fun ListComponent(data: List<DummyData>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight()
    ) {
        items(items = data) { data ->
            Item(data = data)
        }
    }
}

@Composable
fun Item(data: DummyData) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        contentAlignment = Center
    ) {

        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .shadow(5.dp, RoundedCornerShape(5.dp))
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
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(CenterVertically)
                        .fillMaxHeight()
                        .align(CenterVertically)

                ) {
                    Text(
                        textAlign = TextAlign.Justify,
                        text = data.name,
                        modifier = Modifier
                            .weight(0.2f)
                            .align(CenterVertically)
                    )
                    Text(
                        textAlign = TextAlign.Justify,
                        text = data.harga.toString(),
                        modifier = Modifier
                            .weight(0.2f)
                            .align(CenterVertically)
                    )
                    Text(
                        textAlign = TextAlign.Justify,
                        text = data.name,
                        modifier = Modifier
                            .weight(0.2f)
                            .align(CenterVertically)
                    )
                    Text(
                        textAlign = TextAlign.Justify,
                        text = data.harga.toString(),
                        modifier = Modifier
                            .weight(0.2f)
                            .align(CenterVertically)
                    )
                    Text(
                        textAlign = TextAlign.Justify,
                        text = data.name,
                        modifier = Modifier
                            .weight(0.2f)
                            .align(CenterVertically)
                    )


                }

                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(CenterVertically)
                ) {
                    Button(onClick = { /*TODO*/ }, modifier = Modifier.height(35.dp)) {
                        Text(text = "Edit")
                    }

                    Button(
                        onClick = { /*TODO*/ }, modifier = Modifier
                            .padding(start = 5.dp)
                            .height(35.dp)
                    ) {
                        Text(text = "Remove")
                    }
                }

            }
        }


    }


}