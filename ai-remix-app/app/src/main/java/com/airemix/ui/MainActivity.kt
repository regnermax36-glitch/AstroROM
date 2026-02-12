package com.airemix.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airemix.ui.theme.AIRemixTheme
import com.airemix.utils.PermissionHelper
import com.airemix.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        PermissionHelper.handlePermissionResult(
            permissions,
            onAllGranted = {
                // All permissions granted, proceed with app functionality
            },
            onSomeGranted = { granted, denied ->
                Toast.makeText(
                    this,
                    "Some permissions denied: ${denied.joinToString()}",
                    Toast.LENGTH_LONG
                ).show()
            },
            onAllDenied = {
                Toast.makeText(
                    this,
                    "Permissions required for app to function",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }
    
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Handle selected audio file
            handleSelectedAudioFile(it)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check and request permissions
        if (!PermissionHelper.hasRequiredPermissions(this)) {
            val missingPermissions = PermissionHelper.getMissingRequiredPermissions(this)
            permissionLauncher.launch(missingPermissions.toTypedArray())
        }
        
        setContent {
            AIRemixTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        onFilePickerClick = { openFilePicker() }
                    )
                }
            }
        }
        
        // Handle intent if app was opened with an audio file
        handleIntent(intent)
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }
    
    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                intent.data?.let { uri ->
                    handleSelectedAudioFile(uri)
                }
            }
        }
    }
    
    private fun openFilePicker() {
        filePickerLauncher.launch("audio/*")
    }
    
    private fun handleSelectedAudioFile(uri: Uri) {
        // This will be handled by the ViewModel
        // For now, just show a toast
        Toast.makeText(this, "Audio file selected: ${uri.lastPathSegment}", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onFilePickerClick: () -> Unit,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Remix") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "file_selection",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("file_selection") {
                FileSelectionScreen(
                    onFileSelected = { audioFile ->
                        viewModel.setSelectedAudioFile(audioFile)
                        navController.navigate("remix")
                    },
                    onFilePickerClick = onFilePickerClick
                )
            }
            
            composable("remix") {
                RemixScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onHistoryClick = {
                        navController.navigate("history")
                    }
                )
            }
            
            composable("history") {
                HistoryScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRemixSelected = { remixResult ->
                        // Handle remix selection
                        navController.popBackStack()
                    }
                )
            }
            
            composable("settings") {
                SettingsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

