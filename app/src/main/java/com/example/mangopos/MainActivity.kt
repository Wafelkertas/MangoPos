package com.example.mangopos

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mangopos.presentation.ui.navigation.Navigation
import com.example.mangopos.presentation.ui.theme.MangoPosTheme
import com.example.mangopos.utils.generatePdf2
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.utils.PDFUtil.PDFUtilListener
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.util.*
import java.io.File
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : ComponentActivity(

) {


    @InternalAPI
    @ExperimentalPermissionsApi
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // Force Android to open at Landscape Orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        setContent {
            MangoPosTheme {
                val apiToken by remember { mutableStateOf(null) }
                // A surface container using the 'background' color from the theme

                Navigation()


            }
        }
    }
}

