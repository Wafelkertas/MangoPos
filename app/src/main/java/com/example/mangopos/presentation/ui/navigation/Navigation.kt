import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mangopos.presentation.component.AnimatedTabRow
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.presentation.ui.screen.chartscreen.ChartScreen
import com.example.mangopos.presentation.ui.screen.discountscreen.DiscountScreen
import com.example.mangopos.presentation.ui.screen.invoicesscreen.InvoicesScreen
import com.example.mangopos.presentation.ui.screen.transactionscreen.TransactionScreen

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Navigation() {

    val tabData = listOf(
        Screen.Transaction,
        Screen.Invoices,
        Screen.Discount,
        Screen.Chart
    )
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
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
                        .padding(top=5.dp)

                ) {
                    Text(
                        "MangoPos",
                        color = Color.Black,
                        fontSize = 26.sp
                    )


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
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            if (currentDestination != null) {
                AnimatedTabRow(
                    currentDestination = currentDestination,
                    navController = navController,
                    tabData = tabData
                )
            }
            NavHost(navController = navController, startDestination = Screen.Transaction.route) {
                composable(Screen.Invoices.route) { InvoicesScreen() }
                composable(Screen.Transaction.route) { TransactionScreen() }
                composable(Screen.Discount.route) { DiscountScreen() }
                composable(Screen.Chart.route) { ChartScreen() }
                composable(Screen.Setting.route) { SettingScreen() }

            }
        }


    }

}

