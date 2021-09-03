package com.example.mangopos.presentation.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.ui.navigation.Screen
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun FloatingActionButtonComponent(mainViewModel: MainViewModel,
    currentDestination : NavDestination,
    drawerState : BottomDrawerState,
    coroutineScope : CoroutineScope
) {
    if (currentDestination?.route == Screen.Transaction.route && !drawerState.isOpen) {
        Surface(
            color = MaterialTheme.colors.secondary,
            shape = CircleShape,
            elevation = 2.dp
        ) {
            IconButton(onClick = {
                mainViewModel.editOrder.value = true
                coroutineScope.launch { drawerState.open() }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }

    }
}