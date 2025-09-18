package com.example.composebottombar

import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun JetBottomBar(
    items: List<BottomBarItem>,
    selectedRoute: String,
    onItemClick: (BottomBarItem) -> Unit
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = { onItemClick(item) },
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) }
            )
        }
    }
}
