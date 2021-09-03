package com.example.mangopos.presentation.ui.screen.settings


import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangopos.data.objects.dto.MenuItem
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.utils.URIPathHelper
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import io.ktor.util.*

@ExperimentalPermissionsApi
@InternalAPI
@Composable
fun EditMenuScreen(
    uuid: String,
    mainViewModel: MainViewModel
) {
    val menuItem by remember { mainViewModel.editMenu }
    val category by remember { mainViewModel.listOfCategory }
    val accessToken by remember { mainViewModel.accessToken }
    var dropDownMenu by remember { mutableStateOf(false) }




    LaunchedEffect(key1 = true) {
        mainViewModel.filterMenuToBeEdited(uuid = uuid)
    }


    if (category != null && menuItem != null) {

        var menuName by remember { mutableStateOf(value = "${menuItem!!.name}") }
        var menuPrice by remember { mutableStateOf("${menuItem!!.price}") }
        var menuCategory by remember { mutableStateOf("${category[0]!!.name}") }
        var menuDescription by remember { mutableStateOf(value = "${menuItem!!.description}") }
        var menuImage by remember { mutableStateOf(menuItem!!.image) }
        var menuCategoryUuid by remember { mutableStateOf("${category[0]?.uuid}") }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(5.dp),
                color = Color.White,
                shape = RoundedCornerShape(5.dp),
                elevation = 5.dp
            ) {
                Row(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {


                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Edit Menu ${menuItem!!.name} ",
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            OutlinedTextField(
                                label = { Text(text = "Nama Makanan") },
                                value = menuName,
                                onValueChange = { menuName = it },
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).fillMaxWidth(),
                                maxLines = 1
                            )
                            OutlinedTextField(
                                label = { Text(text = "Harga Makanan") },
                                value = menuPrice,
                                onValueChange = { menuPrice = it },
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).fillMaxWidth(),
                                maxLines = 1
                            )
                            OutlinedTextField(
                                label = { Text(text = "Deksripsi Makanan") },
                                value = menuDescription,
                                onValueChange = { menuDescription = it },
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).fillMaxWidth(),
                                maxLines = 2
                            )


                            OutlinedTextField(enabled = false,
                                readOnly = true,
                                label = { Text(text = "Kategori Makanan") },
                                value = menuCategory,
                                onValueChange = { },
                                modifier = Modifier
                                    .padding(top = 5.dp, bottom = 5.dp)
                                    .focusable(enabled = true)
                                    .clickable {
                                        dropDownMenu = true
                                    }.fillMaxWidth()

                            )

                            DropdownMenu(offset = DpOffset(y = (-30).dp, x = 0.dp),
                                expanded = dropDownMenu,
                                onDismissRequest = { dropDownMenu = false }) {

                                category.forEach { category ->

                                    DropdownMenuItem(onClick = {
                                        menuCategory = category!!.name
                                        menuCategoryUuid = category!!.uuid
                                        dropDownMenu = false
                                    }) {
                                        Text(text = category!!.name, textAlign = TextAlign.Center)
                                    }
                                }


                            }

                        }


                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            var imageUri by remember { mutableStateOf<Uri?>(null) }
                            val context = LocalContext.current
                            val bitmap = remember { mutableStateOf<Bitmap?>(null) }
                            val permission =
                                rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
                            var doNotShowRationale by rememberSaveable { mutableStateOf(false) }
                            var realPath by remember { mutableStateOf<String>("") }

                            val uriPathHelper = URIPathHelper()

                            PermissionRequired(
                                permissionState = permission,
                                permissionNotGrantedContent = {
                                    Column {
                                        Text(text = "Grant The Permission")
                                        Button(onClick = { permission.launchPermissionRequest() }) {
                                            Text(text = "Ok!")
                                        }
                                        Button(onClick = { doNotShowRationale = true }) {
                                            Text(text = "Nope")
                                        }
                                    }
                                },
                                permissionNotAvailableContent = {
                                    Text("Permission denied")
                                }) {

                                val filePath =
                                    imageUri?.let {
                                        uriPathHelper.getPath(
                                            LocalContext.current,
                                            it
                                        )
                                    }
                                if (filePath != null) {
                                    realPath = filePath
                                }


                            }


                            val launcher = rememberLauncherForActivityResult(
                                contract =
                                ActivityResultContracts.GetContent()
                            ) { uri: Uri? ->
                                imageUri = uri
                            }

                            imageUri?.let {
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap.value = MediaStore.Images
                                        .Media.getBitmap(context.contentResolver, it)

                                } else {
                                    val source = ImageDecoder
                                        .createSource(context.contentResolver, it)
                                    bitmap.value = ImageDecoder.decodeBitmap(source)
                                }

                                bitmap.value?.let { btm ->
                                    Image(
                                        bitmap = btm.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier.size(400.dp)
                                    )
                                }

                            }

                            if (imageUri == null){
                                val painter = rememberCoilPainter(request = menuImage)
                                Image(
                                    painter = painter,
                                    contentDescription = "makanan",
                                    modifier = Modifier
                                        .fillMaxSize(0.5f)
                                        .align(Alignment.CenterHorizontally),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Button(onClick = {
                                launcher.launch("image/*")
                            }) {
                                Text(text = "Pick image")
                            }
                            Button(onClick = {
                                mainViewModel.updateMenu(
                                    accessToken = accessToken,
                                    menuItem = MenuItem(
                                        categoryUuid = menuCategoryUuid,
                                        createdAt = "",
                                        description = menuDescription,
                                        id = 0,
                                        image = "",
                                        name = menuName,
                                        price = menuPrice,
                                        updatedAt = "",
                                        uuid = menuItem!!.uuid
                                    ),
                                    uri = realPath
                                )

                            }) {
                                Text(text = "Update Menu")
                            }
                        }
                    }


                }
            }

        }
    }
}