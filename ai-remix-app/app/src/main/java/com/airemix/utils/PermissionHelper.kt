package com.airemix.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionHelper {
    
    companion object {
        private val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        
        private val OPTIONAL_PERMISSIONS = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
        )
        
        fun hasRequiredPermissions(context: Context): Boolean {
            return REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        
        fun hasOptionalPermissions(context: Context): Boolean {
            return OPTIONAL_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        
        fun getMissingRequiredPermissions(context: Context): List<String> {
            return REQUIRED_PERMISSIONS.filter { permission ->
                ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
            }
        }
        
        fun getMissingOptionalPermissions(context: Context): List<String> {
            return OPTIONAL_PERMISSIONS.filter { permission ->
                ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
            }
        }
        
        fun requestRequiredPermissions(activity: Activity, requestCode: Int) {
            val missingPermissions = getMissingRequiredPermissions(activity)
            if (missingPermissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    activity,
                    missingPermissions.toTypedArray(),
                    requestCode
                )
            }
        }
        
        fun shouldShowRationale(activity: Activity, permission: String): Boolean {
            return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }
        
        fun getPermissionExplanation(permission: String): String {
            return when (permission) {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_AUDIO -> 
                    "This permission is required to access and read your music files for remixing."
                
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> 
                    "This permission is required to save the remixed audio files to your device."
                
                Manifest.permission.RECORD_AUDIO -> 
                    "This permission allows the app to analyze audio in real-time for better remix quality."
                
                Manifest.permission.MODIFY_AUDIO_SETTINGS -> 
                    "This permission allows the app to optimize audio settings for better playback quality."
                
                Manifest.permission.POST_NOTIFICATIONS -> 
                    "This permission allows the app to show progress notifications during remix processing."
                
                else -> "This permission is required for the app to function properly."
            }
        }
        
        fun createPermissionLauncher(
            fragment: Fragment,
            onPermissionsResult: (Map<String, Boolean>) -> Unit
        ): ActivityResultLauncher<Array<String>> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                onPermissionsResult(permissions)
            }
        }
        
        fun handlePermissionResult(
            permissions: Map<String, Boolean>,
            onAllGranted: () -> Unit,
            onSomeGranted: (granted: List<String>, denied: List<String>) -> Unit,
            onAllDenied: () -> Unit
        ) {
            val granted = permissions.filter { it.value }.keys.toList()
            val denied = permissions.filter { !it.value }.keys.toList()
            
            when {
                denied.isEmpty() -> onAllGranted()
                granted.isNotEmpty() -> onSomeGranted(granted, denied)
                else -> onAllDenied()
            }
        }
    }
}

data class PermissionState(
    val permission: String,
    val isGranted: Boolean,
    val isRequired: Boolean,
    val explanation: String,
    val shouldShowRationale: Boolean = false
) {
    val displayName: String
        get() = when (permission) {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_AUDIO -> "Storage Access"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> "File Writing"
            Manifest.permission.RECORD_AUDIO -> "Audio Recording"
            Manifest.permission.MODIFY_AUDIO_SETTINGS -> "Audio Settings"
            Manifest.permission.POST_NOTIFICATIONS -> "Notifications"
            else -> permission.substringAfterLast('.')
        }
}

