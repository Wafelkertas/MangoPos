package com.example.mangopos.presentation.component


import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mangopos.R
import com.example.mangopos.data.DummyData


@Composable
fun ItemCard(data: DummyData) {
    Box(
        modifier = Modifier
            .padding(start = 5.dp, end = 10.dp, bottom = 10.dp)
            .shadow(elevation = 5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .width(120.dp)
            .height(200.dp)
    )
    /*
    TODO
        Add clickable
    * */
    {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .width(120.dp)
                    .height(200.dp)
                    .background(Color.White)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            ) {
                val image = painterResource(id = R.drawable.makanan)
                Image(
                    painter = image, contentDescription = "makanan",
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
                    text = data.harga.toString(),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .fillMaxWidth()
                        .padding(start = 5.dp)
                )

            }

        }
    }

}

@ExperimentalFoundationApi
@OptIn
@Composable
fun ItemGrid(listMenu:List<DummyData>) {
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 120.dp), modifier = Modifier.padding(10.dp)){
        items(items = listMenu){menu ->
            ItemCard(data = menu)
        }
    }
}