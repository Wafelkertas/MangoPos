package com.example.mangopos.presentation.ui.screen.chartscreen

import androidx.compose.animation.core.Animation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.ma.charts.legend.data.LegendAlignment
import hu.ma.charts.line.LineChart
import hu.ma.charts.line.data.LineChartData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ChartScreen() {
    val coroutineScope = rememberCoroutineScope()
    val lineChartDataModel = LineChartDataModel()


    Surface(
        color = Color.White, shape = RoundedCornerShape(5.dp), elevation = 5.dp, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(5.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {


//            LineChart(data = lineChartData, chartHeight = 500.dp)
com.github.tehras.charts.line.LineChart(lineChartData = lineChartDataModel.lineChartData)


        }


    }
}



