package com.a_blekot.memorize_shloka.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

enum class Permission(val id: String) {
    Camera(Manifest.permission.CAMERA),

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    Notification(Manifest.permission.POST_NOTIFICATIONS)
}