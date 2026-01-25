package com.neuramusic.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null,
    val selectedIcon: ImageVector? = null
) {
    object Home : Screen(
        route = "home",
        title = "Home",
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    )
    
    object Library : Screen(
        route = "library", 
        title = "Library",
        icon = Icons.Outlined.LibraryMusic,
        selectedIcon = Icons.Filled.LibraryMusic
    )
    
    object Search : Screen(
        route = "search",
        title = "Search", 
        icon = Icons.Outlined.Search,
        selectedIcon = Icons.Filled.Search
    )
    
    object Discover : Screen(
        route = "discover",
        title = "Discover",
        icon = Icons.Outlined.Explore,
        selectedIcon = Icons.Filled.Explore
    )
    
    object Profile : Screen(
        route = "profile",
        title = "Profile",
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person
    )
    
    object NowPlaying : Screen(
        route = "now_playing",
        title = "Now Playing"
    )
    
    companion object {
        val bottomNavItems = listOf(Home, Library, Search, Discover, Profile)
    }
}
