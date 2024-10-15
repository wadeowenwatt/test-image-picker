package com.example.test_image_picker

import android.app.Activity
import android.content.Intent
import io.flutter.plugin.common.PluginRegistry
import java.util.concurrent.Executors

class GalleryPicker : PluginRegistry.ActivityResultListener,
    PluginRegistry.RequestPermissionsResultListener {

    val executor = Executors.newSingleThreadExecutor()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        val handlerRunnable: Runnable
        handlerRunnable = Runnable { handleCaptureImageResult(resultCode) }

        executor.execute(handlerRunnable)
        return true
    }

    private fun pickMediaFromGallery() {
        val pickerIntent: Intent
    }

    private fun handleCaptureImageResult(resultCode: Int) {
        if (resultCode == Activity.RESULT_OK) {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>?,
        grantResults: IntArray?
    ): Boolean {
        return true
    }
}