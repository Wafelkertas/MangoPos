package com.example.mangopos.data.objects.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chart(
    @SerialName("data")
     val listChartData: List<ChartItem>
)

@Serializable
data class ChartItem(
    @SerialName("month")
    val month: String,
    @SerialName("sum")
    val sum: String,
    @SerialName("year")
    val year: String
)

