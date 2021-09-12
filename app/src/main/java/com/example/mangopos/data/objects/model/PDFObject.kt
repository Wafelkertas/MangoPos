package com.example.mangopos.data.objects.model

import com.example.mangopos.data.objects.dto.Cart

data class PDFObject(
    val customerName:String,
    val sumTotal:String,
    val discount:String,
    val noInvoice:String,
    val date:String,
    val uuid:String,
    val listOfCarts: List<Cart>,
    val change:String,
    val customerCash:String
)