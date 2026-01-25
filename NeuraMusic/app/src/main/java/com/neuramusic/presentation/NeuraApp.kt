package com.neuramusic.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neuramusic.presentation.component.SimpleBottomNavigation
import com.neuramusic.presentation.navigation.Screen
import com.neuramusic.presentation.screen.*
import com.neuramusic.presentation.viewmodel.AIEnhancedMusicViewModel

@Composable
fun NeuraApp(
    musicViewModel: AIEnhancedMusicViewModel,
    navController: NavHostController = rememberNavController()
) {
    var currentRoute by remember { mutableStateOf(Screen.Home.route) }
    
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.NowPlaying.route) {
                SimpleBottomNavigation(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    currentRoute = Screen.Home.route
                    HomeScreen(
                        musicViewModel = musicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate(Screen.NowPlaying.route)
                        }
                    )
                }
                
                composable(Screen.Library.route) {
                    currentRoute = Screen.Library.route
                    LibraryScreen(
                        musicViewModel = musicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate(Screen.NowPlaying.route)
                        }
                    )
                }
                
                composable(Screen.Search.route) {
                    currentRoute = Screen.Search.route
                    SearchScreen(
                        musicViewModel = musicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate(Screen.NowPlaying.route)
                        }
                    )
                }
                
                composable(Screen.Discover.route) {
                    currentRoute = Screen.Discover.route
                    DiscoverScreen(
                        musicViewModel = musicViewModel,
                        onNavigateToNowPlaying = {
                            navController.navigate(Screen.NowPlaying.route)
                        }
                    )
                }
                
                composable(Screen.Profile.route) {
                    currentRoute = Screen.Profile.route
                    ProfileScreen(
                        musicViewModel = musicViewModel
                    )
                }
                
                composable(Screen.NowPlaying.route) {
                    currentRoute = Screen.NowPlaying.route
                    NowPlayingScreen(
                        musicViewModel = musicViewModel,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
