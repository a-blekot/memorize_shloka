package com.a_blekot.memorize_shloka.permissions

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import java.lang.ref.WeakReference
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Singleton



object PermissionManager {

    private var activity = WeakReference<ComponentActivity>(null)

    private var permissionLauncher: ActivityResultLauncher<String>? = null

    private val resultSharedFlow = MutableSharedFlow<Boolean>()

    fun attachActivity(activity: ComponentActivity) {
        this.activity = WeakReference(activity)
        val contract = ActivityResultContracts.RequestPermission()
        permissionLauncher = activity.registerForActivityResult(contract) { granted ->
            activity.lifecycleScope.launch {
                resultSharedFlow.emit(granted)
            }
        }
    }

    fun detachActivity(activity: ComponentActivity) {
        if (this.activity.get() == activity) {
            permissionLauncher?.unregister()
            this.activity.clear()
        }
    }

    suspend fun requestPermissionIfNotGranted(permission: Permission): PermissionResult {
        return activity.get()!!.run {
            when {
                hasPermission(permission) -> {
                    PermissionResult.Granted
                }
                requestPermission(permission) -> {
                    PermissionResult.Granted
                }
                wasPermissionDeniedForever(permission, wasPermissionRequestedBefore = true) -> {
                    PermissionResult.DeniedForever
                }
                else -> {
                    PermissionResult.Denied
                }
            }
        }
    }

    fun checkPermission(
        permission: Permission,
        wasPermissionRequestedBefore: Boolean,
    ): PermissionResult {
        return activity.get()!!.run {
            when {
                hasPermission(permission) -> {
                    PermissionResult.Granted
                }
                wasPermissionDeniedForever(permission, wasPermissionRequestedBefore) -> {
                    PermissionResult.DeniedForever
                }
                else -> {
                    PermissionResult.Denied
                }
            }
        }
    }

    private fun ComponentActivity.hasPermission(permission: Permission): Boolean {
        return ContextCompat.checkSelfPermission(this, permission.id) ==
            PackageManager.PERMISSION_GRANTED
    }

    private fun ComponentActivity.wasPermissionDeniedForever(
        permission: Permission,
        wasPermissionRequestedBefore: Boolean,
    ): Boolean {
        return wasPermissionRequestedBefore && !shouldShowRequestPermissionRationale(permission.id)
    }

    suspend fun requestPermission(permission: Permission): Boolean {
        permissionLauncher?.launch(permission.id)
        return resultSharedFlow.first()
    }
}