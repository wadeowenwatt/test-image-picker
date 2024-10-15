package com.example.test_image_picker

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import java.io.File


class MainActivity : FlutterActivity() {
    private val CHANNEL = "HEHEHE"
    private val LISTENER_CHANNEL = "HAHAHA"
    private val PERMISSION_REQUEST_CODE = 8386
    private val PICK_IMAGE = 123

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            when (call.method) {
                "getImageAndVideoPermission" -> {
                    getImageAndVideoPermission(result)
                }

                "getImage" -> {
                    val intent = Intent()
                    intent.setType("image/*")
                    intent.setAction(Intent.ACTION_GET_CONTENT)
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE
                    )
                }
            }
        }
    }

    private fun getImageAndVideoPermission(result: MethodChannel.Result) {
        /// For android 13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VIDEO
            )
            if (hasPermission(permissions)) {
                result.success(true)
            } else {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
                result.success(false)
            }
        }
    }

    private fun hasPermission(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                val uri = data?.data
                if (uri?.path != null) {
                    val pathImage = getPathFromUri(uri)
                    MethodChannel(
                        flutterEngine!!.dartExecutor.binaryMessenger,
                        LISTENER_CHANNEL
                    ).invokeMethod("imagePickerResult", pathImage)
                }
            }
        }
    }

    private fun getPathFromUri(uri: Uri): String {
        val projection = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = cursor?.getString(columnIndex ?: 0)
        cursor?.close()
        return path ?: ""
    }
}
