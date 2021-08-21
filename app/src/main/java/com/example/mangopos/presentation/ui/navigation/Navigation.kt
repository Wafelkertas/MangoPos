import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mangopos.presentation.component.AnimatedTabRow

import com.example.mangopos.presentation.component.FloatingActionButtonComponent
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.presentation.ui.screen.chartscreen.ChartScreen
import com.example.mangopos.presentation.ui.screen.invoicesscreen.InvoicesScreen
import com.example.mangopos.presentation.ui.screen.loginscreen.LoginScreen
import com.example.mangopos.presentation.ui.screen.orderscreen.OrderScreen
import com.example.mangopos.presentation.ui.screen.registerscreen.RegisterScreen
import com.example.mangopos.presentation.ui.screen.transactionscreen.TransactionScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Navigation() {
    val tabData = listOf(
        Screen.Transaction,
        Screen.Invoices,
        Screen.Chart
    )

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (!drawerState.isOpen) {

                TopAppBar(
                    elevation = 0.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(MaterialTheme.colors.primary)
                            .padding(top = 5.dp)

                    ) {
                        if (currentDestination?.route == Screen.Setting.route) {
                            IconButton(onClick = {
                                navController.navigate(Screen.Transaction.route)
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

                        if (currentDestination?.route != Screen.Login.route) {
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
            if (currentDestination != null && currentDestination.route != Screen.Login.route) {
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
                    coroutineScope = coroutineScope
                )
            }


        }
    ) {
        BottomDrawer(
            gesturesEnabled = false,
            drawerState = drawerState,
            content = {
                NavHost(navController = navController, startDestination = Screen.Login.route) {
                    composable(Screen.Login.route) {
                        LoginScreen(
                            navHostController = navController,
                            drawerState = drawerState
                        )
                    }
                    composable(Screen.Invoices.route) { InvoicesScreen() }
                    composable(Screen.Transaction.route) { TransactionScreen() }
                    composable(Screen.Chart.route) { ChartScreen() }
                    composable(Screen.Setting.route) { SettingScreen() }
                }
            },
            drawerContent = {


                if (currentDestination?.route == Screen.Transaction.route) {
                    OrderScreen(coroutineScope = coroutineScope, drawerState = drawerState)
                }

                RegisterScreen(navHostController = navController, drawerState = drawerState)


            }
        )


    }

}




