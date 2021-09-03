package com.example.mangopos.presentation.ui.navigation

import androidx.annotation.StringRes
import com.example.mangopos.R

sealed class Screen(
    val route: String,
    @StringRes val screenString: Int
){
    object Invoices:Screen(route = "Invoices", screenString = R.string.Invoices)
    object Discount:Screen(route = "Discount", screenString = R.string.Discount)
    object Chart:Screen(route = "Chart", screenString = R.string.Chart)
    object Setting:Screen(route = "Setting", screenString = R.string.Setting)
    object Transaction:Screen(route = "Transaction", screenString = R.string.Transaction)
    object Login:Screen(route = "Login", screenString = R.string.Login)
    object MainScreen:Screen(route = "MainScreen", screenString = R.string.MainScreen)
    object EditMenu:Screen(route = "EditMenu", screenString = R.string.EditMenu)
    object CreateMenu:Screen(route = "CreateMenu", screenString = R.string.CreateMenu)
    object OrderMenu:Screen(route = "OrderMenu", screenString = R.string.OrderMenu)
    object Order:Screen(route = "Order", screenString = R.string.Order)
}
