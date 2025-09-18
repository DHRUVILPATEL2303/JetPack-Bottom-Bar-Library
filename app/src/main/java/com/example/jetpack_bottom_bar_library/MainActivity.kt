package com.example.jetpack_bottom_bar_library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.composebottombar.AutoHideBottomNavigationBar
import com.example.composebottombar.BottomBarItem
import com.example.composebottombar.FarmerBottomBarItem
import com.example.composebottombar.JetBottomBar
import com.example.jetpack_bottom_bar_library.ui.theme.Jetpack_Bottom_Bar_LibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jetpack_Bottom_Bar_LibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AutoHideBottomNavigationBar(
                        selectedItemIndex = 0,
                        items = listOf(
                            FarmerBottomBarItem(
                                name = "Home",
                                route = "home",
                                selectedIcon = Icons.Filled.Home,
                                unselectedIcon = Icons.Filled.Home,
                                color = Color.Blue
                            ),
                            FarmerBottomBarItem(
                                name = "Profile",
                                route = "profile",
                                selectedIcon = Icons.Filled.Person,
                                unselectedIcon = Icons.Filled.Person,
                                color = Color.Green
                            )
                        ),
                        isDarkTheme = false,
                        isExpanded = true,
                        onItemSelected = { index -> println("Selected index $index") },
                        onExpandRequested = { println("Expand requested") }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Jetpack_Bottom_Bar_LibraryTheme {
        Greeting("Android")
    }
}

val items = listOf(
    BottomBarItem("Home", Icons.Default.Home, "home"),
    BottomBarItem("Profile", Icons.Default.Person, "profile")
)

