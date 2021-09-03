package com.example.mangopos.data.objects.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Chart : ArrayList<ChartItem>()

@Serializable
data class ChartItem(
    @SerialName("month")
    val month: String,
    @SerialName("sum")
    val sum: String,
    @SerialName("year")
    val year: String
)