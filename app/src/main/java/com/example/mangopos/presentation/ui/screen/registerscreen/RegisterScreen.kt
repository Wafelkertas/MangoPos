package com.example.mangopos.presentation.ui.screen.registerscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.presentation.ui.theme.fff6c2
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    drawerState: BottomDrawerState
) {

    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(fff6c2),
        contentAlignment = Alignment.Center
    ) {


        Surface(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(5.dp))
                .requiredHeight(400.dp)
                .width(400.dp)

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Surface(shape = RoundedCornerShape(topStart = 5.dp,topEnd = 5.dp, bottomStart = 0.dp,bottomEnd = 0.dp),
                    elevation = 2.dp,
                    modifier = Modifier
                        .height(100.dp)
                        .width(400.dp),
                    color = MaterialTheme.colors.secondary
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(text = "Register", fontSize = 24.sp)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                ) {

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        label = {
                            Text(
                                text = "Email"
                            )
                        })
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        label = {
                            Text(
                                text = "password"
                            )
                        })
                    Button(
                        onClick = { navHostController.navigate(Screen.Transaction.route) },
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Text(text = "Register",fontSize = 18.sp, color = Color.Black)
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch { drawerState.close() }

                        },
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Text(text = "Cancel",fontSize = 18.sp, color = Color.Black)
                    }
                }
            }

        }
    }
}