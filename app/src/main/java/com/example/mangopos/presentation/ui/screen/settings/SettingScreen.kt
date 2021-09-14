import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.ui.theme.FCEE86
import kotlinx.coroutines.launch
import com.example.mangopos.data.objects.dto.MenuItem
import com.example.mangopos.presentation.component.EditMenuGrid
import com.example.mangopos.presentation.ui.navigation.Screen
import com.example.mangopos.data.objects.model.PDFObject


@ExperimentalFoundationApi
@Composable
fun SettingScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    menuItem: List<MenuItem>
) {

    LaunchedEffect(key1 = true) {
        mainViewModel.createMenuStatus.value = null
        mainViewModel.updateMenuStatus.value = null
        mainViewModel.editMenu.value = null
    }
    val scope = rememberCoroutineScope()
    val state = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(FCEE86),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .padding(5.dp),
                color = Color.White,
                elevation = 5.dp,
                shape = RoundedCornerShape(5.dp)
            ) {

                EditMenuGrid(
                    listMenus = menuItem,
                    mainViewModel = mainViewModel,
                    cartUuid = "",
                    navController = navController
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                val context = LocalContext.current



                Button(modifier = Modifier.padding(start = 5.dp, end = 5.dp), onClick = {
                    navController.navigate("Login")
                    scope.launch { mainViewModel.signOut() }
                }) {
                    Text(text = "Sign Out")
                }

                Button(modifier = Modifier.padding(start = 5.dp, end = 5.dp), onClick = {
                    navController.navigate(Screen.CreateMenu.route)

                }) {
                    Text(text = "Add menu")
                }
                Button(modifier = Modifier.padding(start = 5.dp, end = 5.dp), onClick = {


                }) {
                    Text(text = "Discount")
                }
                Button(modifier = Modifier.padding(start = 5.dp, end = 5.dp), onClick = {
                    mainViewModel.createPdf(
                        application = context, PDFObject(
                            customerName = "Kocak",
                            sumTotal = "Kocak",
                            discount = "Kocak",
                            noInvoice = "Kocak",
                            date = "Kocak",
                            uuid = "Kocak",
                            listOfCarts = listOf(),
                            change = "kocak",
                            customerCash = "kocak"
                        )
                    )

                }) {
                    Text(text = "Test PDF")
                }
            }
        }
    }
}