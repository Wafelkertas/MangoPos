package com.example.mangopos.presentation.ui.screen.invoicesscreen

import android.app.Activity
import android.net.Uri
import android.print.PrintAttributes
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.R
import com.example.mangopos.data.objects.model.PDFObject
import com.example.mangopos.presentation.MainViewModel

import com.tejpratapsingh.pdfcreator.utils.FileManager
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import java.io.File

@Composable
fun DetailInvoicesScreen(
    noInvoices: String,
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val context = LocalContext.current
    val detailInvoices by remember { mainViewModel.invoicesDetail }
    val createPdfStatus by remember { mainViewModel.createPdfStatus }

    val accessToken by remember { mainViewModel.accessToken }


    LaunchedEffect(key1 = true) {
        mainViewModel.createPdfStatus.value = null
        mainViewModel.getInvoicesDetail(noInvoices = noInvoices, accessToken = accessToken)
    }
    Log.d("createPdfStatus", createPdfStatus.toString())


    if (detailInvoices == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(modifier = Modifier.size(100.dp))
        }
    }
    if (detailInvoices != null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f),
                elevation = 5.dp,
                shape = RoundedCornerShape(5.dp)
            ) {


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    item {


                        Text(
                            text = "Invoices No. ${detailInvoices!!.noInvoice}",
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(bottom = 10.dp, top = 10.dp)
                        )
                        Image(
                            painterResource(id = R.drawable.mangomansecircle),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.2f)
                                .padding(5.dp)
                        )
                        Text(
                            text = "Jl. Bukit Sawangan Indah No.8, Duren Mekar, Bojongsari, Depok City, West Java 16518",
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(top = 10.dp, bottom = 1.dp), thickness = 2.dp
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(bottom = 10.dp, top = 0.dp), thickness = 2.dp
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Text(text = "Nama Customer")
                            Text(text = detailInvoices!!.name)
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(top = 10.dp, bottom = 1.dp), thickness = 2.dp
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(bottom = 10.dp, top = 0.dp), thickness = 2.dp
                        )
                    }
                    items(detailInvoices!!.carts) { items ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = items.menuName)
                            Text(text = items.quantity.toString())
                            Text(text = items.price)
                        }
                    }
                    item {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(top = 10.dp, bottom = 1.dp), thickness = 2.dp
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(bottom = 10.dp, top = 0.dp), thickness = 2.dp
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            Text(text = "Total Price")
                            Text(text = detailInvoices!!.totalPrice)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            Text(text = "Total Disc")
                            Text(text = (detailInvoices!!.totalPriceAfterDiscount.toInt() - detailInvoices!!.totalPrice.toInt()).toString())
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            Text(text = "Total Belanja")
                            Text(text = detailInvoices!!.totalPriceAfterDiscount)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            Text(text = "Cash Out")
                            Text(text = detailInvoices!!.cash)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            Text(text = "Change")
                            Text(text = detailInvoices!!.moneyChange)
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(top = 20.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Button(
                                onClick = {
                                    mainViewModel.createPdf(
                                        application = context,
                                        pdfObject = PDFObject(
                                            customerName = detailInvoices!!.name,
                                            sumTotal = detailInvoices!!.totalPriceAfterDiscount,
                                            discount = (detailInvoices!!.totalPriceAfterDiscount.toInt()-detailInvoices!!.totalPrice.toInt()).toString(),
                                            noInvoice = detailInvoices!!.noInvoice,
                                            date = "",
                                            uuid = detailInvoices!!.uuid,
                                            listOfCarts = detailInvoices!!.carts,
                                            change = detailInvoices!!.moneyChange,
                                            customerCash = detailInvoices!!.cash
                                        )
                                    )
                                },
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(text = "Cetak")
                            }
                            Button(
                                enabled = createPdfStatus == true, onClick = {
                                    val newFileManager =
                                        FileManager.getInstance().getTempFile(context, "invoices.pdf")
                                    val newPdfUri = Uri.fromFile(newFileManager)

                                    printPdf(context as Activity, fileToPrint = newFileManager)
                                },
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(text = "Print")
                            }

                        }
                    }
                }
            }
        }

    }
}


fun printPdf(activity: Activity, fileToPrint: File) {
    val printAttributes = PrintAttributes.Builder()
    printAttributes.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
    printAttributes.setMinMargins(PrintAttributes.Margins.NO_MARGINS)
    PDFUtil.printPdf(activity, fileToPrint, printAttributes.build())
}