package id.jsaproject.flognest.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LogoDev
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem (
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

val navItems = listOf(
    NavItem("Home", Icons.Rounded.Home, Screen.Home),
    NavItem("About", Icons.Rounded.LogoDev, Screen.About)
)