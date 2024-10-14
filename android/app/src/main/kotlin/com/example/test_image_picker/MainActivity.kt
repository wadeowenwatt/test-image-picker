package com.example.test_image_picker

import android.app.Instrumentation.ActivityResult
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity() {
    private val CHANNEL = "HEHEHE"
    private val PERMISSION_REQUEST_CODE = 8386

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            when (call.method) {
                "getImageAndVideoPermission"-> {
                    getImageAndVideoPermission(result)
                }
                "" -> {

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
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}
