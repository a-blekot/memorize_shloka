package com.a_blekot.memorize_shloka.inapp

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.a_blekot.memorize_shloka.R
import com.a_blekot.shlokas.common.resources.MR.strings
import com.a_blekot.shlokas.common.resources.resolve
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE
import com.google.android.play.core.install.model.InstallStatus.DOWNLOADED
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import io.github.aakira.napier.Napier

private const val UPDATE_REQUEST_CODE = 108_108

class InappUpdater(private val activity: Activity) {

    private var listener: InstallStateUpdatedListener? = InstallStateUpdatedListener { installState ->
        if (installState.installStatus() == DOWNLOADED) {
            Napier.d("An update has been downloaded", tag = "InappUpdater")
            showDialogForCompleteUpdate()
        }
    }

    private val appUpdateManager: AppUpdateManager =
        AppUpdateManagerFactory.create(activity).apply {
            listener?.let { registerListener(it) }
        }

    fun clean() {
        listener?.let { appUpdateManager.unregisterListener(it) }
        listener = null
    }

    fun checkUpdate() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.flexibleUpdateAvailable()) {
                    Napier.d("Update available", tag = "InappUpdater")
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, FLEXIBLE, activity, UPDATE_REQUEST_CODE)
                } else {
                    Napier.d("No updates", tag = "InappUpdater")
                }
            }
    }

    private fun showDialogForCompleteUpdate() {
        AlertDialog.Builder(activity, R.style.AlertDialogStyle)
            .setTitle(strings.label_update_is_ready.resolve(activity))
            .setPositiveButton(strings.label_restart.resolve(activity)) { _, _ ->
                restartApp()
            }
            .create()
            .show()
    }

    private fun restartApp() =
        appUpdateManager.run {
            clean()
            completeUpdate()
        }

    private fun AppUpdateInfo.flexibleUpdateAvailable() =
        updateAvailability() == UPDATE_AVAILABLE && isUpdateTypeAllowed(FLEXIBLE)
}