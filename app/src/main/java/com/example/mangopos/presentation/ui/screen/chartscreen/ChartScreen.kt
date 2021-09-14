package com.example.mangopos.presentation.ui.screen.chartscreen

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.mangopos.data.objects.dto.Chart
import com.example.mangopos.data.objects.dto.ChartItem
import com.example.mangopos.databinding.ActivityChartBinding
import com.example.mangopos.presentation.MainViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun ChartScreen(mainViewModel: MainViewModel) {


    val accessToken by remember { mainViewModel.accessToken }
    val chartData by remember { mainViewModel.listOfChartItem }
    var chartEntry1 by remember { mutableStateOf<List<BarEntry>>(listOf()) }

    if (accessToken.isNotEmpty()) {
        LaunchedEffect(key1 = true) {
            mainViewModel.getChartData(accessToken = accessToken)

        }
    }

    if (chartData.isNotEmpty()) {
        LaunchedEffect(key1 = true) {
            Log.d("chartDataNotEmpty", "chartDataNotEmpty")
            chartEntry1 = chartDtoToChartEntry(chartData)
        }
    }
    Log.d("chartData", chartData.toString())
    Log.d("chartEntry", chartEntry1.toString())
    Log.d("chartScreen", accessToken.toString())

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
            if (chartEntry1.isNotEmpty()) {

                val dataset = BarDataSet(chartEntry1, "Penjualan")

                val barData = BarData(dataset)
                ChartViewBinding(dataset = dataset, barData = barData)
            }


        }


    }
}

@Composable
fun ChartViewBinding(barData: BarData, dataset: BarDataSet) {
    var animateState by remember { mutableStateOf(false)}

    AndroidViewBinding(ActivityChartBinding::inflate) {
        val chart = this.barChart
        val monthXAxisFormatter = MonthXAxisFormatter(chart)
        val rupiahValueFormatter = RupiahValueFormatter()
        dataset.valueFormatter = rupiahValueFormatter
        chart.data = barData

        // Set up label for X-Axis
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.typeface = Typeface.DEFAULT_BOLD
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = 12
        xAxis.valueFormatter = monthXAxisFormatter

        // Set up label for Y-Axis
        val leftAxis = chart.axisLeft
        leftAxis.valueFormatter = rupiahValueFormatter

        if (!animateState){
        chart.animateY(1000)
        animateState = true
        }


    }
}

fun chartDtoToChartEntry(chart: List<ChartItem>): List<BarEntry> {
    val chartEntry = chart.map { chartItem ->
        BarEntry(sortMonths(chartItem.month), (chartItem.sum.toInt() / 1000).toFloat())
    }
    return chartEntry
}

fun sortMonths(month: String): Float {
    var value = 0f
    when (month) {
        "1" -> value = 1f
        "2" -> value = 2f
        "3" -> value = 3f
        "4" -> value = 4f
        "5" -> value = 5f
        "6" -> value = 6f
        "7" -> value = 7f
        "8" -> value = 8f
        "9" -> value = 9f
        "10" -> value = 10f
        "11" -> value = 11f
        "12" -> value = 12f
    }
    return value
}

class RupiahValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "Rp.${value.roundToInt()}000"
    }
}

class MonthXAxisFormatter(theChart: BarChart) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {

        var month = ""
        when (value) {
            1f -> {
                month = "January"
            }
            2f -> {
                month = "February"
            }
            3f -> {
                month = "March"
            }
            4f -> {
                month = "April"
            }
            5f -> {
                month = "May"
            }
            6f -> {
                month = "June"
            }
            7f -> {
                month = "July"
            }
            8f -> {
                month = "August"
            }
            9f -> {
                month = "September"
            }
            10f -> {
                month = "October"
            }
            11f -> {
                month = "November"
            }
            12f -> {
                month = "December"
            }

        }
        Log.d("getFormatted", "$value $month")
        return month
    }


}





