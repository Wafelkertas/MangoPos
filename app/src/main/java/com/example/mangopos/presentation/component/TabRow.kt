package com.example.mangopos.presentation.component

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.mangopos.presentation.ui.navigation.Screen

@Composable
fun TabRow(
    navController: NavController,
    tabData: List<Screen>
) {
    var tabIndex by remember { mutableStateOf(0) }



    TabRow(
        backgroundColor = Color(0xFFffDD49),
        selectedTabIndex = tabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .padding(0.dp)
            .shadow(10.dp)

    ) {
        tabData.forEachIndexed { index, screen ->
            Tab(selected = screen.route == navController.currentDestination?.route , onClick = {
                tabIndex = index
                navController.navigate(screen.route){
                    if (navController.currentDestination != null){

                    popUpTo(navController.currentDestination!!.route!!) {
                        inclusive = true
                        saveState = true
                    }
                    }

                    restoreState = true
                    launchSingleTop = true
                }
            }, text = { Text(text = screen.route, color = Color.Black, fontSize = 18.sp) })
        }
    }


}


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AnimatedTabRow(
    currentDestination: NavDestination,
    navController: NavController,
    tabData: List<Screen>,
    drawerState: BottomDrawerState
) {
    var visible by remember {
        mutableStateOf(true)
    }



    if (drawerState.isClosed || (!currentDestination.hierarchy.any() { it.route == Screen.Setting.route } ) ) {
        visible = true
    }
    if (drawerState.isExpanded || (currentDestination.hierarchy.any() { it.route == Screen.Setting.route })) {
        visible = false
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { fullWidth -> -fullWidth / 3 }) + fadeIn(
            animationSpec = tween(durationMillis = 300, delayMillis = 100)
        ),
        exit = slideOutVertically(targetOffsetY = { fullWidth -> -fullWidth / 3 }) + fadeOut(
            animationSpec = tween(durationMillis = 300, delayMillis = 100)
        )
    ) {

        TabRow(
            navController = navController,
            tabData = tabData
        )


    }
}