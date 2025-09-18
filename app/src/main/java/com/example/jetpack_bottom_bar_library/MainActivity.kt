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
import androidx.compose.ui.tooling.preview.Preview
import com.example.composebottombar.BottomBarItem
import com.example.composebottombar.JetBottomBar
import com.example.jetpack_bottom_bar_library.ui.theme.Jetpack_Bottom_Bar_LibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jetpack_Bottom_Bar_LibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JetBottomBar(
                        items = items,
                        selectedRoute = "home",
                        onItemClick = { item -> println("Clicked ${item.label}") }
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

