package com.a_blekot.memorize_shloka.bottom_sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.a_blekot.memorize_shloka.R
import com.a_blekot.memorize_shloka.bottom_sheet.controller.BottomSheetClassicController
import com.a_blekot.memorize_shloka.bottom_sheet.controller.BottomSheetSuspendableController
import com.a_blekot.memorize_shloka.bottom_sheet.controller.BottomSheetSuspendableControllerImpl
import com.a_blekot.shlokas.android_ui.custom.BottomSheetCard
import com.a_blekot.shlokas.android_ui.theme.AppTheme

/**
 * You have to add
 * line "implementation(libs.material)"
 * in build.gradle file to use it
 * */
abstract class BottomSheetFragment : BottomSheetDialogFragment() {

    private val isDialogShowing
        get() =
            dialog?.isShowing == true

    protected open val alwaysExpanded = false

    protected val controller: BottomSheetClassicController
        by lazy {
            BottomSheetClassicController(this.dialog as BottomSheetDialog)
        }

    protected val suspendableController: BottomSheetSuspendableController
        by lazy {
            BottomSheetSuspendableControllerImpl(controller)
        }

    @Composable
    protected abstract fun BottomSheetContent()

    @CallSuper
    protected open fun initBottomSheet() {
        if (alwaysExpanded) {
            controller.expandAndDisableHalfScreen()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        initBottomSheet()
        return ComposeView(inflater.context).apply {
            setContent {
                AppTheme {
                    BottomSheetCard {
                        BottomSheetContent()
                    }
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(
            requireContext(),
            R.style.MyTransparentBottomSheetDialogTheme
        ).apply {
            window?.let {
                WindowCompat.setDecorFitsSystemWindows(it, false)
            }
        }
    }

    override fun dismiss() {
        if (isAdded && isDialogShowing) {
            super.dismissAllowingStateLoss()
        }
    }
}
