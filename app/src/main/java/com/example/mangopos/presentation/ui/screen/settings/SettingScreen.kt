import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mangopos.presentation.MainViewModel
import com.example.mangopos.presentation.component.MenuGrid
import com.example.mangopos.presentation.ui.theme.FCEE86
import kotlinx.coroutines.launch
import com.example.mangopos.data.objects.dto.MenuItem
import com.example.mangopos.presentation.component.EditMenuGrid
import com.example.mangopos.presentation.ui.navigation.Screen


@ExperimentalFoundationApi
@Composable
fun SettingScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    menuItem: List<MenuItem>
) {

    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FCEE86)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
        ) {
            Surface(
                Modifier
                    .fillMaxWidth()
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
                    .fillMaxWidth(0.5f)
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(onClick = {
                    navController.navigate("Login")
                    scope.launch { mainViewModel.signOut() }
                }) {
                    Text(text = "Sign Out")
                }
                Button(onClick = {
                    navController.navigate(Screen.CreateMenu.route)

                }) {
                    Text(text = "Add menu")
                }
                Button(onClick = {
                    navController.navigate("Login")

                }) {
                    Text(text = "Discount")
                }
            }
        }
    }
}