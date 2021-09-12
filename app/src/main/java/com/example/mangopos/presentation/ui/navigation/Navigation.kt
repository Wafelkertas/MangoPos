package com.example.mangopos.presentation.ui.navigation

import SettingScreen
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.component.AnimatedTabRow
import com.google.accompanist.navigation.animation.composable

import com.example.mangopos.presentation.component.FloatingActionButtonComponent
import com.example.mangopos.presentation.ui.screen.chartscreen.ChartScreen
import com.example.mangopos.presentation.ui.screen.checkoutscreen.CheckOutScreen

import com.example.mangopos.presentation.ui.screen.ediorderscreen.EditOrderScreen
import com.example.mangopos.presentation.ui.screen.invoicesscreen.DetailInvoicesScreen
import com.example.mangopos.presentation.ui.screen.invoicesscreen.InvoicesScreen
import com.example.mangopos.presentation.ui.screen.loginscreen.LoginScreen
import com.example.mangopos.presentation.ui.screen.orderscreen.OrderScreen
import com.example.mangopos.presentation.ui.screen.registerscreen.RegisterScreen
import com.example.mangopos.presentation.ui.screen.settings.CreateMenuScreen
import com.example.mangopos.presentation.ui.screen.settings.EditMenuScreen
import com.example.mangopos.presentation.ui.screen.transactionscreen.TransactionScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@InternalAPI
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val tabData = listOf(
        Screen.Transaction,
        Screen.Invoices,
        Screen.Chart
    )

    val listMenuItem by remember { mainViewModel.listOfMenu }
    val editOrderState by remember { mainViewModel.editOrder }
    val listOfInvoices by remember { mainViewModel.listOfInvoices }
    val listOfOrder by remember { mainViewModel.listOfOrder }
    val accessToken by remember { mainViewModel.accessToken }


    val navController = rememberAnimatedNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    if (accessToken.isNotEmpty()) {
        LaunchedEffect(key1 = true) {
            mainViewModel.getAllOrder(accessToken = accessToken)
        }
    }




    Scaffold(
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    modifier = Modifier.border(2.dp, MaterialTheme.colors.secondary)
                ) {
                    Box {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = data.message)
                            if (mainViewModel.networkErrorLogin.value == null && currentDestination?.route == "Login") {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        },
        scaffoldState = scaffoldState,
        topBar = {
            if (!drawerState.isOpen && currentDestination?.route != Screen.CheckOutScreen.route) {

                TopAppBar(
                    elevation = 0.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color.Black)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(MaterialTheme.colors.primary)
                            .padding(top = 5.dp)

                    ) {
                        if (currentDestination?.route == Screen.Setting.route || currentDestination?.route == "${Screen.EditMenu.route}/{uuid}" || currentDestination?.route == Screen.CreateMenu.route) {
                            IconButton(onClick = {
                                if (currentDestination.route == Screen.Setting.route) {
                                    navController.navigate(Screen.Transaction.route)
                                }

                                if (currentDestination.route == "${Screen.EditMenu.route}/{uuid}") {
                                    mainViewModel.editMenu.value = null
                                    navController.navigate(Screen.Setting.route)
                                }

                                if (currentDestination.route == Screen.CreateMenu.route) {
                                    navController.navigate(Screen.Setting.route)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.Black
                                )
                            }
                        } else {
                            Text(
                                "MangoPos",
                                color = Color.Black,
                                fontSize = 26.sp,
                                fontWeight = FontWeight(200)
                            )
                        }

                        if (currentDestination?.route != Screen.Login.route && currentDestination?.route != "${Screen.EditMenu.route}/{uuid}" && currentDestination?.route != Screen.Setting.route && currentDestination?.route != Screen.CreateMenu.route && currentDestination?.route != Screen.CheckOutScreen.route) {
                            IconButton(onClick = {
                                navController.navigate(Screen.Setting.route)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.Black
                                )
                            }
                        }

                    }


                }

            }

        },
        bottomBar = {
            if (currentDestination != null && currentDestination.route != Screen.Login.route && currentDestination.route != "${Screen.EditMenu.route}/{uuid}" && currentDestination?.route != Screen.CreateMenu.route && currentDestination?.route != Screen.CheckOutScreen.route && currentDestination?.route != "${Screen.DetailOrder.route}/{no_invoices}") {
                AnimatedTabRow(
                    currentDestination = currentDestination,
                    navController = navController,
                    tabData = tabData,
                    drawerState = drawerState
                )
            }
        },
        floatingActionButton = {

            if (currentDestination != null) {
                FloatingActionButtonComponent(
                    currentDestination = currentDestination,
                    drawerState = drawerState,
                    coroutineScope = coroutineScope,
                    mainViewModel = mainViewModel
                )
            }


        }
    ) {

        BottomDrawer(
            gesturesEnabled = false,
            drawerState = drawerState,
            content = {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screen.Login.route
                ) {

                    composable(Screen.Login.route,
                        exitTransition = { _, target ->
                            when (target.destination.route) {
                                Screen.Transaction.route -> {
                                    slideOutHorizontally(
                                        targetOffsetX = { fullWidth -> -fullWidth / 5 },
                                        animationSpec = tween(300)
                                    ) + fadeOut(animationSpec = tween(300))
                                }
                                else -> null
                            }
                        },
                        enterTransition = { initial, _ ->
                            when (initial.destination.route) {
                                Screen.Transaction.route ->
                                    slideInHorizontally(
                                        initialOffsetX = { fullWidth -> -fullWidth / 5 },
                                        animationSpec = tween(300)
                                    ) + fadeIn(animationSpec = tween(300))
                                else -> null
                            }
                        }, popEnterTransition = { initial, _ ->
                            when (initial.destination.route) {
                                Screen.Transaction.route ->
                                    slideInHorizontally(
                                        initialOffsetX = { fullWidth -> -fullWidth / 5 },
                                        animationSpec = tween(300)
                                    ) + fadeIn(animationSpec = tween(300))
                                else -> null
                            }
                        }
                    ) {
                        LoginScreen(
                            scaffoldState = scaffoldState,
                            navHostController = navController,
                            drawerState = drawerState,
                            mainViewModel = mainViewModel
                        )
                    }
                    composable(Screen.Invoices.route) { InvoicesScreen(mainViewModel = mainViewModel, navController = navController) }
                    composable(Screen.CheckOutScreen.route) {
                        CheckOutScreen(
                            mainViewModel = mainViewModel,
                            navController = navController,
                            drawerState = drawerState
                        )
                    }
                    composable(Screen.CreateMenu.route) {
                        CreateMenuScreen(
                            mainViewModel = mainViewModel,
                            navController = navController
                        )
                    }
                    composable(
                        "${Screen.EditMenu.route}/{uuid}",
                        arguments = listOf(navArgument("uuid") { type = NavType.StringType })
                    ) { backStackEntry ->
                        EditMenuScreen(
                            mainViewModel = mainViewModel,
                            uuid = backStackEntry.arguments?.getString("uuid")
                                .toString(), navController = navController
                        )
                    }
                    composable(
                        "${Screen.DetailOrder.route}/{no_invoices}",
                        arguments = listOf(navArgument("no_invoices") { type = NavType.StringType })
                    ) { backStackEntry ->
                        DetailInvoicesScreen(
                            noInvoices = backStackEntry.arguments?.getString("no_invoices")
                                .toString(), navController = navController,
                            mainViewModel = mainViewModel

                        )
                    }
                    composable(Screen.Transaction.route,
                        enterTransition = { initial, _ ->
                            when (initial.destination.route) {
                                Screen.Login.route -> {
                                    slideInHorizontally(
                                        initialOffsetX = { fullWidth -> -fullWidth / 5 },
                                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                                    )
                                    fadeIn(animationSpec = tween(300))
                                }
                                else -> null
                            }
                        }, exitTransition = { _, target ->
                            when (target.destination.route) {
                                Screen.Login.route ->
                                    slideOutHorizontally(
                                        targetOffsetX = { fullWidth -> -fullWidth / 5 },
                                        animationSpec = tween(300)
                                    ) + fadeOut(animationSpec = tween(300))
                                else -> null
                            }
                        }, popExitTransition = { _, target ->
                            when (target.destination.route) {
                                Screen.Login.route ->
                                    slideOutHorizontally(
                                        targetOffsetX = { fullWidth -> -fullWidth / 5 },
                                        animationSpec = tween(300)
                                    ) + fadeOut(animationSpec = tween(300))
                                else -> null
                            }
                        }) {

                        TransactionScreen(
                            mainViewModel = mainViewModel,
                            scaffoldState = scaffoldState,
                            drawerState = drawerState,
                            navController = navController,
                            listOfOrder = listOfOrder
                        )


                    }
                    composable(Screen.Chart.route) {
                        ChartScreen(mainViewModel = mainViewModel)

                    }


                    composable(Screen.Setting.route) {
                        SettingScreen(
                            navController = navController,
                            mainViewModel = mainViewModel,
                            menuItem = listMenuItem
                        )
                    }
                }
            },
            drawerContent = {

                if (currentDestination?.route == Screen.Transaction.route && editOrderState == false) {
                    EditOrderScreen(
                        drawerState = drawerState,
                        menuItemList = listMenuItem,
                        mainViewModel = mainViewModel,
                        navController = navController
                    )
                }

                if (currentDestination?.route == Screen.Transaction.route && editOrderState == true) {
                    OrderScreen(
                        coroutineScope = coroutineScope,
                        drawerState = drawerState,
                        menuItemList = listMenuItem,
                        mainViewModel = mainViewModel,
                        accessToken = accessToken
                    )
                }

                RegisterScreen(navHostController = navController, drawerState = drawerState)


            }
        )


    }

}




