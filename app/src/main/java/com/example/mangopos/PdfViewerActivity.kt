package com.example.mangopos

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.print.PrintAttributes
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scaleMatrix
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.lifecycleScope
import com.example.mangopos.databinding.ActivityPdfViewerBinding
import com.example.mangopos.databinding.ItemPdfViewerBinding
import com.tejpratapsingh.pdfcreator.activity.PDFViewerActivity
import com.tejpratapsingh.pdfcreator.custom.TouchImageViewFling

import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.utils.PDFUtil.PDFUtilListener
import com.tejpratapsingh.pdfcreator.views.PDFBody
import com.tejpratapsingh.pdfcreator.views.PDFFooterView
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.util.*

  class PdfViewerActivity : PDFViewerActivity() {

    private lateinit var binding: ActivityPdfViewerBinding
    val PDF_FILE_URI = "pdfFileUri"
    private var pdfData: File? = null
    var bitmapPdfList = LinkedList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        pdfData = intent.getParcelableExtra(PDF_FILE_URI)
        bitmapPdfList = PDFUtil.pdfToBitmap(pdfData)

        lifecycleScope.launch {
            delay(2000)
            Log.d("pdfData", pdfData.toString())
        }


        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPagerPdfViewer

        viewPager.adapter = PdfViewerPagerAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            bitmap = bitmapPdfList,
            pdfFile = pdfFile,
            activity = this
        )


    }



      class PdfViewerPagerAdapter(fm: FragmentManager, behavior: Int, bitmap:LinkedList<Bitmap>, pdfFile:File, activity: PdfViewerActivity) :
          FragmentStatePagerAdapter(fm, behavior) {
          private val bitmapData = bitmap
          private val pdfFileData = pdfFile
          private var pdfViewerActivity = activity


          override fun getItem(position: Int): Fragment {
              val fragment: Fragment = PdfFragment(bitmap = bitmapData,pdfFile = pdfFileData, activity = pdfViewerActivity)
              val args = Bundle()
              // Our object is just an integer :-P
              args.putInt(PdfPageFragment.ARG_POSITION, position)
              fragment.arguments = args
              return fragment
          }

          override fun getCount(): Int {
              return this.bitmapData.size
          }


      }



    class PdfFragment(bitmap: LinkedList<Bitmap>, pdfFile: File, activity: PdfViewerActivity) : PdfPageFragment() {
        private var bitmapData = bitmap
        private var fileToPrint = pdfFile
        private var pdfViewerActivity = activity
        private var pdfFragmentBinding : ItemPdfViewerBinding? = null

        private var _binding: ItemPdfViewerBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            _binding = ItemPdfViewerBinding.inflate(inflater, container, false)

            if (fileToPrint == null)
            Log.d("zoom", binding.imageViewItemPdfViewer.currentZoom.toString())
            val printAttributes = PrintAttributes.Builder()
            printAttributes.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            printAttributes.setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            binding.floatingActionButton.setOnClickListener{
                Log.d("printPdf", "printPdf")
                PDFUtil.printPdf(pdfViewerActivity, fileToPrint, printAttributes.build())
            }


            binding.imageViewItemPdfViewer.setImageBitmap(bitmapData.first)

            val view = binding.root
            return view
        }


        fun printPdf(){
            val printAttributes = PrintAttributes.Builder()
            printAttributes.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            printAttributes.setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            PDFUtil.printPdf(pdfViewerActivity, fileToPrint, printAttributes.build())
        }








        override fun onDestroy() {
            super.onDestroy()
            pdfFragmentBinding = null
//            _binding = null
        }
    }
}



