package com.airemix.data

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import com.airemix.models.AudioFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

class FileManager(private val context: Context) {
    
    private val remixOutputDir: File by lazy {
        File(context.getExternalFilesDir(null), "remixes").apply {
            if (!exists()) mkdirs()
        }
    }
    
    private val tempDir: File by lazy {
        File(context.cacheDir, "temp_audio").apply {
            if (!exists()) mkdirs()
        }
    }
    
    suspend fun getAudioFileFromUri(uri: Uri): AudioFile? = withContext(Dispatchers.IO) {
        try {
            val cursor = context.contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )
            
            cursor?.use { c ->
                if (c.moveToFirst()) {
                    val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = c.getColumnIndex(OpenableColumns.SIZE)
                    
                    val displayName = if (nameIndex >= 0) c.getString(nameIndex) else "Unknown"
                    val size = if (sizeIndex >= 0) c.getLong(sizeIndex) else 0L
                    
                    val mimeType = context.contentResolver.getType(uri) ?: "audio/mpeg"
                    
                    // Try to get additional metadata from MediaStore
                    val mediaStoreInfo = getMediaStoreInfo(uri)
                    
                    AudioFile(
                        id = UUID.randomUUID().toString(),
                        uri = uri,
                        name = displayName.substringBeforeLast('.'),
                        displayName = displayName,
                        size = size,
                        duration = mediaStoreInfo?.duration ?: 0L,
                        mimeType = mimeType,
                        path = mediaStoreInfo?.path,
                        artist = mediaStoreInfo?.artist,
                        album = mediaStoreInfo?.album,
                        bitrate = mediaStoreInfo?.bitrate,
                        sampleRate = mediaStoreInfo?.sampleRate
                    )
                } else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun getMediaStoreInfo(uri: Uri): MediaStoreInfo? {
        return try {
            val projection = arrayOf(
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.BITRATE
            )
            
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    MediaStoreInfo(
                        duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)),
                        path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)),
                        artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)),
                        album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)),
                        bitrate = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.BITRATE))
                    )
                } else null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun copyUriToTempFile(uri: Uri, fileName: String): File? = withContext(Dispatchers.IO) {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.use { input ->
                val tempFile = File(tempDir, fileName)
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
                tempFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    suspend fun saveRemixToOutput(
        tempFile: File,
        originalFileName: String,
        remixSuffix: String = "remix"
    ): File? = withContext(Dispatchers.IO) {
        try {
            val baseName = originalFileName.substringBeforeLast('.')
            val extension = originalFileName.substringAfterLast('.', "mp3")
            val outputFileName = "${baseName}_${remixSuffix}_${System.currentTimeMillis()}.${extension}"
            val outputFile = File(remixOutputDir, outputFileName)
            
            tempFile.copyTo(outputFile, overwrite = true)
            outputFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun getRemixOutputDirectory(): File = remixOutputDir
    
    fun getTempDirectory(): File = tempDir
    
    fun cleanupTempFiles() {
        try {
            tempDir.listFiles()?.forEach { file ->
                if (file.isFile && System.currentTimeMillis() - file.lastModified() > 24 * 60 * 60 * 1000) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun deleteFile(file: File): Boolean {
        return try {
            file.delete()
        } catch (e: Exception) {
            false
        }
    }
    
    fun getFileExtension(fileName: String): String {
        return fileName.substringAfterLast('.', "")
    }
    
    fun getMimeTypeFromExtension(extension: String): String? {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase())
    }
    
    private data class MediaStoreInfo(
        val duration: Long,
        val path: String?,
        val artist: String?,
        val album: String?,
        val bitrate: Int?,
        val sampleRate: Int? = null
    )
}

