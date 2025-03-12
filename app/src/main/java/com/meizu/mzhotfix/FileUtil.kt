package com.meizu.mzhotfix

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * author : Samuel
 * e-mail : xingtai.wei@xjmz.com
 * time   : 2025/3/5 下午6:00
 * desc   :
 */
object FileUtil {

    fun copyAssetFileToPrivateDir(context: Context, assetFileName: String) {
        val outputFilePath = context.filesDir.absolutePath + File.separator + "patch" + File.separator + assetFileName
        val outputFile = File(outputFilePath)
        outputFile.parentFile?.mkdirs() // 确保目录存在
        val inputStream: InputStream = context.assets.open(assetFileName)
        val outputStream: OutputStream = FileOutputStream(outputFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputFile.setReadOnly()
        inputStream.close()
        outputStream.close()
    }

}