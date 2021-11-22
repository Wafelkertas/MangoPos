package com.example.mangopos.utils

import android.util.Log
import com.example.mangopos.data.objects.dto.*

import android.os.Environment


import android.content.Context
import android.graphics.*

import androidx.core.content.ContextCompat

import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.mangopos.R
import com.example.mangopos.R.*
import com.example.mangopos.data.objects.model.PDFObject
import com.tejpratapsingh.pdfcreator.utils.FileManager
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.basic.PDFHorizontalView
import com.tejpratapsingh.pdfcreator.views.basic.PDFImageView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import com.tejpratapsingh.pdfcreator.views.basic.PDFVerticalView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Error
import java.lang.Exception


fun menuToCartItem(menu: MenuItem, cartUuid: String): MenuItem {
    return (
            MenuItem(
                image = "",
                name = menu.name,
                price = menu.price,
                menuUuid = menu.menuUuid,
                quantity = 1,
                slug = ""
            )
            )
}

fun addItemToList(list: List<MenuItem>, oldItem: MenuItem): List<MenuItem> {

    val newItem = MenuItem(
        image = oldItem.image,
        name = oldItem.name,
        menuUuid = oldItem.menuUuid,
        price = oldItem.price,
        quantity = oldItem.quantity + 1,
        slug = oldItem.slug
    )
    val position = list.indexOf(oldItem)

    Log.d("quantity", newItem.quantity.toString())
    val newList = list.filterNot { data ->
        data == oldItem
    } as MutableList
    newList.add(position, newItem)
    return newList
}

fun removeItemToList(list: List<MenuItem>, oldItem: MenuItem): List<MenuItem> {

    val newItem = MenuItem(
        image = oldItem.image,
        name = oldItem.name,
        menuUuid = oldItem.menuUuid,
        price = oldItem.price,
        quantity = oldItem.quantity - 1,
        slug = oldItem.slug

    )
    val position = list.indexOf(oldItem)



    Log.d("quantity", newItem.quantity.toString())
    val newList = list.filterNot { data ->
        data == oldItem
    } as MutableList

    newList.add(position, newItem)
    return newList
}

fun newAddedToList(list: List<InvoicesItem>, oldItem: List<InvoicesItem>): List<InvoicesItem> {

    var newList = mutableListOf<InvoicesItem>()
    newList.addAll(list)
    newList.addAll(oldItem)



    return newList
}


fun deleteItemFromList(list: List<MenuItem>, oldItem: MenuItem): List<MenuItem> {


    val newList = list.filterNot { data ->
        Log.d("filtering", "${oldItem.menuUuid}, ${data.menuUuid}")
        data.menuUuid == oldItem.menuUuid
    }
    return newList
}

fun generatePDF(appContext: Context, bitmap: Bitmap) {


    // creating an object variable
    // for our PDF document.
    val pdfDocument = PdfDocument()


    // two variables for paint "paint" is used
    // for drawing shapes and we will use "title"
    // for adding text in our PDF file.
    val paint = Paint()
    val title = Paint()
    val pageHeight = 360
    val pageWidth = 288

    // we are adding page info to our PDF file
    // in which we will be passing our pageWidth,
    // pageHeight and number of pages and after that
    // we are calling it to create our PDF.
    val mypageInfo = PageInfo.Builder(pageWidth, pageHeight, 1).create()

    // below line is used for setting
    // start page for our PDF file.
    val myPage = pdfDocument.startPage(mypageInfo)

    // creating a variable for canvas
    // from our page of PDF.
    val canvas: Canvas = myPage.canvas

    // below line is used to draw our image on our PDF file.
    // the first parameter of our drawbitmap method is
    // our bitmap
    // second parameter is position from left
    // third parameter is position from top and last
    // one is our variable for paint.
    val bitmapWidth = bitmap.width


    canvas.drawBitmap(bitmap, ((pageWidth - bitmapWidth) / 2).toFloat(), 40f, paint)

    // below line is used for adding typeface for
    // our text which we will be adding in our PDF file.
    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    // below line is used for setting text size
    // which we will be displaying in our PDF file.
    title.textSize = 8f

    // below line is sued for setting color
    // of our text inside our PDF file.
//    title.color = ContextCompat.getColor(appContext, R.color.white)

    // below line is used to draw text in our PDF file.
    // the first parameter is our text, second parameter
    // is position from start, third parameter is position from top
    // and then we are passing our variable of paint which is title.


    // similarly we are creating another text and in this
    // we are aligning this text to center of our PDF file.
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(appContext, color.black)


    // below line is used for setting
    // our text to center of PDF.
    title.textAlign = Paint.Align.CENTER
    canvas.drawText(
        "This is sample document which we have created.",
        (pageWidth / 2).toFloat(),
        50f,
        title
    )
    canvas.drawText(
        "This is sample document which we have created.",
        (pageWidth / 2).toFloat(),
        59f,
        title
    )
    canvas.drawText("A portal for IT professionals.", (pageWidth - 40).toFloat(), 80f, title)
    canvas.drawText("Geeks for Geeks", (pageWidth / 2).toFloat(), 90f, title)

    // after adding all attributes to our
    // PDF file we will be finishing our page.
    pdfDocument.finishPage(myPage)

    // below line is used to set the name of
    // our PDF file and its path.
    val file = File(Environment.getExternalStorageDirectory(), "GFG.pdf")
    try {
        // after creating a file name we will
        // write our PDF file to that location.
        pdfDocument.writeTo(FileOutputStream(file))
        Log.d("pdfDocument", "$file")

        // below line is to print toast message
        // on completion of PDF generation.


    } catch (e: IOException) {
        // below line is used
        // to handle error
        Log.d("pdfDocument", "$e")
        e.printStackTrace()
    }
    // after storing our pdf to that
    // location we are closing our PDF file.
    pdfDocument.close()
}

fun generatePdf2(appContext: Context, bitmap: Bitmap, pdfObject: PDFObject)  {


    val fileManager = FileManager.getInstance()
    fileManager.cleanTempFolder(appContext)

    val view: MutableList<View> = arrayListOf()
    val verticalPdf = PDFVerticalView(appContext)
    val horizontalPdf = PDFHorizontalView(appContext)
    verticalPdf.setLayout(
        LinearLayout.LayoutParams(
            400,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
    )

    val text1 = PDFTextView(appContext, PDFTextView.PDF_TEXT_SIZE.P).setText(pdfObject.customerName)
    val text2 = PDFTextView(appContext, PDFTextView.PDF_TEXT_SIZE.P).setText(pdfObject.sumTotal)
    val text3 = PDFTextView(appContext, PDFTextView.PDF_TEXT_SIZE.P).setText(pdfObject.noInvoice)
    val image = PDFImageView(appContext).setImageBitmap(bitmap)


    text1.view.gravity = Gravity.CENTER_HORIZONTAL
    text2.view.gravity = Gravity.CENTER_HORIZONTAL
    horizontalPdf.addView(image)
    horizontalPdf.view.gravity = Gravity.CENTER_HORIZONTAL



    verticalPdf.addView(horizontalPdf)
    verticalPdf.addView(text1)
    verticalPdf.addView(text2)
    verticalPdf.addView(text3)

    val verticalPdfView = verticalPdf.view
    view.add(verticalPdfView)


    val filename = "test"


    try {
        Log.d("Test", "test")
        PDFUtil.getInstance().generatePDF(
            view, fileManager.createTempFileWithName(
                appContext,
                "$filename.pdf", false
            ).absolutePath, object : PDFUtil.PDFUtilListener {
                override fun pdfGenerationSuccess(savedPDFFile: File?) {
                    Log.d("pdfGenerationSuccess", "pdfGenerationSuccess")

                }

                override fun pdfGenerationFailure(exception: Exception?) {
                    Log.d("pdfGenerationFailed", "pdfGenerationFailed")

                }
            })

    } catch (e: Error) {
        Log.d("IOException", e.toString())

    }


}








