package com.a_blekot.memorize_shloka.notifications

import android.os.Parcelable
import androidx.compose.runtime.Composable
import com.a_blekot.memorize_shloka.bottom_sheet.BottomSheetFragment
import com.a_blekot.memorize_shloka.fragments.setScreenResult
import kotlinx.parcelize.Parcelize

class NotificationsPermissionDialogFragment : BottomSheetFragment() {

    @Parcelize
    data class ScreenResult(
        val allowed: Boolean,
    ) : Parcelable

    override val alwaysExpanded = true

    @Composable
    override fun BottomSheetContent() {
        NotificationsPermissionBottomSheet(
            onTurnOnClick = {
                setScreenResult(RESULT_KEY, ScreenResult(true))
                dismiss()
            },
            onSkipClick = {
                dismiss()
            }
        )
    }

    companion object {
        const val RESULT_KEY = "NotificationsPermissionDialogFragment"
    }
}
