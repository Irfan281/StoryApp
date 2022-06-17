package com.irfan.storyapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.io.InputStreamReader

object JsonReader {
    fun readStringFromFile(filename: String): String {
        try {
            val applicationContext = ApplicationProvider.getApplicationContext<Context>()
            val inputStream = applicationContext.assets.open(filename)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }

            return builder.toString()
        } catch (e: Exception) {
            throw e
        }
    }
}