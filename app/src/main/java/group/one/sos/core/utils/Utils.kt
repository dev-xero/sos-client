package group.one.sos.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object Utils {
    fun saveBitmapAndGetUri(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.cacheDir, "incident_${System.currentTimeMillis()}.jpg")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        stream.flush()
        stream.close()
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
}