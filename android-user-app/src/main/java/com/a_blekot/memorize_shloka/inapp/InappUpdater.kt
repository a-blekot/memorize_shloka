package com.a_blekot.memorize_shloka.inapp

import android.app.Activity
import android.app.ProgressDialog.show
import android.content.Context
import android.util.Log
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import com.a_blekot.memorize_shloka.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE
import com.google.android.play.core.install.model.InstallStatus.DOWNLOADED
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import io.github.aakira.napier.Napier

private const val UPDATE_REQUEST_CODE = 108_108

class InappUpdater(val context: Activity) {

    private val listener: InstallStateUpdatedListener = InstallStateUpdatedListener { installState ->
        if (installState.installStatus() == DOWNLOADED) {
            Napier.d("An update has been downloaded", tag = "InappUpdater")
            restartApp()
        }
    }

    private val appUpdateManager: AppUpdateManager =
        AppUpdateManagerFactory.create(context).apply {
            registerListener(listener)
        }

    fun clear() {
        appUpdateManager.unregisterListener(listener)
    }

    fun checkUpdate() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.flexibleUpdateAvailable()) {
                    Napier.d("Update available", tag = "InappUpdater")
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, FLEXIBLE, context, UPDATE_REQUEST_CODE)
                } else {
                    Napier.d("No updates", tag = "InappUpdater")
                }
            }
    }

    private fun showSnackbarForCompleteUpdate() {
//        Snackbar.make(
//            findViewById(R.id.activity_main_layout),
//            "An update has just been downloaded.",
//            Snackbar.LENGTH_INDEFINITE
//        ).apply {
//            setAction("RESTART") { restartApp() }
//            setActionTextColor(resources.getColor(R.color.snackbar_action_text_color))
//            show()
//        }
    }

    private fun restartApp() =
        appUpdateManager.run {
            unregisterListener(listener)
            completeUpdate()
        }

    private fun AppUpdateInfo.flexibleUpdateAvailable() =
        updateAvailability() == UPDATE_AVAILABLE && isUpdateTypeAllowed(FLEXIBLE)
}