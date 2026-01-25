package com.neuramusic.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neuramusic.presentation.screen.*
import com.neuramusic.presentation.viewmodel.AIEnhancedMusicViewModel
import com.neuramusic.ui.theme.*

@Composable
fun GlassNeuraApp(
    aiMusicViewModel: AIEnhancedMusicViewModel,
    navController: NavHostController = rememberNavController()
) {
    var currentRoute by remember { mutableStateOf("home") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GlassBackgroundDark,
                        GlassSurface.copy(alpha = 0.8f),
                        GlassBackgroundDark
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            bottomBar = {
                if (currentRoute != "nowplaying") {
                    GlassBottomNavigation(
                        navController = navController,
                        currentRoute = currentRoute
                    )
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("home") {
                    currentRoute = "home"
                    HomeScreen(
                        musicViewModel = aiMusicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate("nowplaying")
                        }
                    )
                }
                
                composable("library") {
                    currentRoute = "library"
                    LibraryScreen(
                        musicViewModel = aiMusicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate("nowplaying")
                        }
                    )
                }
                
                composable("search") {
                    currentRoute = "search"
                    SearchScreen(
                        musicViewModel = aiMusicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate("nowplaying")
                        }
                    )
                }
                
                composable("discover") {
                    currentRoute = "discover"
                    DiscoverScreen(
                        musicViewModel = aiMusicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate("nowplaying")
                        }
                    )
                }
                
                composable("profile") {
                    currentRoute = "profile"
                    ProfileScreen(
                        musicViewModel = aiMusicViewModel
                    )
                }
                
                composable("nowplaying") {
                    currentRoute = "nowplaying"
                    NowPlayingScreen(
                        musicViewModel = aiMusicViewModel,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GlassBottomNavigation(
    navController: NavHostController,
    currentRoute: String
) {
    val items = listOf(
        GlassNavItem("home", "Home", Icons.Default.Home),
        GlassNavItem("library", "Library", Icons.Default.LibraryMusic),
        GlassNavItem("search", "Search", Icons.Default.Search),
        GlassNavItem("discover", "Discover", Icons.Default.Explore),
        GlassNavItem("profile", "Profile", Icons.Default.Person)
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassCard
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        NavigationBar(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            modifier = Modifier.padding(8.dp)
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { 
                        Icon(
                            item.icon, 
                            contentDescription = item.label,
                            tint = if (currentRoute == item.route) GlassNeuralPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        ) 
                    },
                    label = { 
                        Text(
                            item.label,
                            color = if (currentRoute == item.route) GlassNeuralPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        ) 
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo("home")
                                launchSingleTop = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GlassNeuralPrimary,
                        selectedTextColor = GlassNeuralPrimary,
                        indicatorColor = GlassNeuralPrimary.copy(alpha = 0.2f),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        }
    }
}

data class GlassNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
