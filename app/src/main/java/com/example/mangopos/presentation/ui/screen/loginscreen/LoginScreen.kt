package com.example.mangopos.presentation.ui.screen.loginscreen

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.presentation.ui.theme.fff6c2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun LoginScreen(
    scaffoldState: ScaffoldState,
    navHostController: NavHostController,
    drawerState: BottomDrawerState,
    mainViewModel: MainViewModel
) {
    val coroutineScope = rememberCoroutineScope()


    var email by remember { mutableStateOf("Mongos") }
    var password by remember { mutableStateOf("password") }

    val error by remember { mainViewModel.networkErrorLogin }
    val accessToken by remember { mainViewModel.accessToken }
    val networkErrorMessage by remember { mainViewModel.networkLoginErrorMessage }
    val sharedPreferences by remember { mainViewModel.sharePref }

    Log.d("error", "$error cause: $networkErrorMessage")
    Log.d("accessToken", "access-Token : $accessToken")



    LaunchedEffect(key1 = true) {
        mainViewModel.loadSharePrefToState()
    }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(fff6c2),
        contentAlignment = Alignment.Center
    ) {


        if (sharedPreferences.isNotBlank()) {

            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    "Login Success",
                )

                mainViewModel.networkErrorLogin.value = null
                mainViewModel.getMenuList(accessToken = accessToken)
//                    mainViewModel.getAllOrder(accessToken = accessToken)
                mainViewModel.getAllCategory(accessToken = accessToken)
                delay(500)
                navHostController.navigate(Screen.Transaction.route) {
                    popUpTo("Login") {
                        inclusive = true
                    }
                }
            }
        }


        when (error) {
            true -> {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(networkErrorMessage)
                }
            }
            false -> {
                if (accessToken.isNotBlank()) {
//                    mainViewModel.getInvoicesList(accessToken = accessToken)
                    coroutineScope.launch {
                        mainViewModel.getMenuList(accessToken = accessToken)
                        scaffoldState.snackbarHostState.showSnackbar(
                            "Login Success",
                        )
                        mainViewModel.getInvoicesList(accessToken = accessToken)
                        mainViewModel.networkErrorLogin.value = null
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        delay(500)
                        navHostController.navigate(Screen.Transaction.route) {
                            popUpTo("Login") {
                                inclusive = true
                            }
                        }


                    }

                }
            }
            null -> Unit
        }


        Surface(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(5.dp))
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.5f)

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Surface(
                    shape = RoundedCornerShape(
                        topStart = 5.dp,
                        topEnd = 5.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ),
                    elevation = 2.dp,
                    modifier = Modifier
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.secondary
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(text = "Sign In", fontSize = 24.sp)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {


                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            if (it.length < 10) email = it
                        },

                        label = {
                            Text(
                                text = "Email"
                            )
                        })
                    OutlinedTextField(visualTransformation = PasswordVisualTransformation(),
                        value = password,
                        onValueChange = {
                            if (it.length < 10) password = it
                        },
                        label = {
                            Text(
                                text = "password"
                            )
                        })
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if (email.isBlank() || password.isBlank()) {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Input Email and Password")
                                    }

                                }
                                if (email.isNotBlank() && password.isNotBlank()) {
                                    mainViewModel.networkErrorLogin.value = null
                                    mainViewModel.signIn(username = email, password = password)

                                }

                            },
                            modifier = Modifier.padding(
                                top = 5.dp,
                                bottom = 5.dp,
                                start = 5.dp,
                                end = 5.dp
                            ),
                            colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.secondary)
                        ) {
                            Text(text = "Login", fontSize = 18.sp, color = Color.Black)
                        }
                        Button(
                            onClick = {
                                coroutineScope.launch { drawerState.open() }

                            },
                            modifier = Modifier.padding(
                                top = 5.dp,
                                bottom = 5.dp,
                                start = 5.dp,
                                end = 5.dp
                            ),
                            colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.secondary)
                        ) {
                            Text(text = "Register", fontSize = 18.sp, color = Color.Black)
                        }
                    }
                }
            }

        }


    }
}

