package group.one.sos.core.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun Context.uriToFile(uri: Uri): File? {
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val tempFile = File.createTempFile("incident_", ".jpg")
    tempFile.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
    return tempFile
}