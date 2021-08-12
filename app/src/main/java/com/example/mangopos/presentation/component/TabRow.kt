package com.example.mangopos.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.mangopos.presentation.ui.navigation.Screen

@Composable
fun TabRow(
    navController: NavController,
    tabData: List<Screen>
) {
    var tabIndex by remember { mutableStateOf(0) }



    androidx.compose.material.TabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(50.dp)
            .padding(0.dp)
            .shadow(10.dp)

    ) {
        tabData.forEachIndexed { index, screen ->
            Tab(selected = tabIndex == index, onClick = {
                tabIndex = index
                navController.navigate(screen.route)
            }, text = { Text(text = screen.route, color = Color.Black, fontSize = 18.sp) })
        }
    }


}


@ExperimentalAnimationApi
@Composable
fun AnimatedTabRow(
    currentDestination: NavDestination,
    navController: NavController,
    tabData: List<Screen>
) {
    var visible by remember {
        mutableStateOf(true)
    }

    if (!currentDestination?.hierarchy?.any() { it.route == Screen.Setting.route }) {
        visible = true
    } else if (currentDestination?.hierarchy?.any() { it.route == Screen.Setting.route }) {
        visible = false
    }
    AnimatedVisibility(
        visible = visible
    ) {

        TabRow(
            navController = navController,
            tabData = tabData
        )


    }
}