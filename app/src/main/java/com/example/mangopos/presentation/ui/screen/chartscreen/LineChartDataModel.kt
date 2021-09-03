package com.example.mangopos.presentation.ui.screen.chartscreen

import android.graphics.Typeface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.line.renderer.point.HollowCircularPointDrawer
import com.github.tehras.charts.line.renderer.point.NoPointDrawer
import com.github.tehras.charts.line.renderer.point.PointDrawer
import hu.ma.charts.legend.data.LegendAlignment
import hu.ma.charts.legend.data.LegendPosition
import hu.ma.charts.line.data.AxisLabel
import hu.ma.charts.line.data.ChartColors
import hu.ma.charts.line.data.DrawAxis
import hu.ma.charts.line.data.LineChartData


val value = 100..1000 step 100

val yLabel = value.map {
    AxisLabel(it.toFloat(), "Rp.${it}.000")
}


val months = mapOf(
    ("1" to "January"),
    ("2" to "February"),
    ("3" to "March"),
    ("4" to "April"),
    ("5" to "May"),
    ("6" to "June"),
    ("7" to "July"),
    ("8" to "August"),
    ("9" to "September"),
    ("10" to "October"),
    ("11" to "November"),
    ("12" to "December")
)


val lineChartData = LineChartData(
    series = listOf(
        LineChartData.SeriesData(
            "title",
            points = listOf(LineChartData.SeriesData.Point(1, 1000f), LineChartData.SeriesData.Point(7, 500f), LineChartData.SeriesData.Point(12, 900f)),
            color = Color.Blue,
            gradientFill = true
        )
    ),
    xLabels = months.map { it.value },
    yLabels = yLabel,
    chartColors = ChartColors.defaultColors(),
    horizontalLines = false,
    axisWidth = 0.0f,
    axisTextSize = 10.sp,
    axisTypeface = Typeface.DEFAULT,
    axisLabelPadding = 0.dp,
    drillDownIndicatorStrokeWidth = 10.dp,
    legendPosition = LegendPosition.Top,
    legendAlignment = LegendAlignment.Center,
    legendOffset = 5.dp,
    legendShapeSize = 10.dp,
    legendShape = RectangleShape
)

class LineChartDataModel {
    var lineChartData by mutableStateOf(
        com.github.tehras.charts.line.LineChartData(
            points = listOf(
                com.github.tehras.charts.line.LineChartData.Point(randomYValue(), "Label1"),
                com.github.tehras.charts.line.LineChartData.Point(randomYValue(), "Label2"),
                com.github.tehras.charts.line.LineChartData.Point(randomYValue(), "Label3"),
                com.github.tehras.charts.line.LineChartData.Point(randomYValue(), "Label4"),
                com.github.tehras.charts.line.LineChartData.Point(randomYValue(), "Label5"),
                com.github.tehras.charts.line.LineChartData.Point(randomYValue(), "Label6"),
                com.github.tehras.charts.line.LineChartData.Point(randomYValue(), "Label7")
            )
        )
    )
    var horizontalOffset by mutableStateOf(5f)
    var pointDrawerType by mutableStateOf(PointDrawerType.Filled)
    val pointDrawer: PointDrawer
        get() {
            return when (pointDrawerType) {
                PointDrawerType.None -> NoPointDrawer
                PointDrawerType.Filled -> FilledCircularPointDrawer()
                PointDrawerType.Hollow -> HollowCircularPointDrawer()
            }
        }

    private fun randomYValue(): Float = (100f * Math.random()).toFloat() + 45f

    enum class PointDrawerType {
        None,
        Filled,
        Hollow
    }
}

