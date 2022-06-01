package com.example.android.overlaypermission

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.asynclayoutinflater.view.AsyncLayoutInflater

class SampleService : Service() {

    lateinit var frameView: View
    lateinit var windowManager: WindowManager

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        setupUi()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun setupUi() {
        listOf(
            R.layout.chromeos_view
        ).forEach { id ->
            AsyncLayoutInflater(this).inflate(id, null) { view, resId, _ ->
                when (resId) {
                    R.layout.chromeos_view -> {
                        frameView = view
                        windowManager.addView(frameView, getLayoutParams())
                    }
                }
            }

        }
    }

    private fun getLayoutParams(): WindowManager.LayoutParams {
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.START or Gravity.TOP
            x = 0
            y = 0
            alpha = 0.8F
        }
    }
}