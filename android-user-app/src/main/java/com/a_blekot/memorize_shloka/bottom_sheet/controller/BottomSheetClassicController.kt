package com.a_blekot.memorize_shloka.bottom_sheet.controller

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetClassicController(
    private val dialog: BottomSheetDialog,
) {

    fun expand() {
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun enableHalfScreen() {
        dialog.behavior.skipCollapsed = false
    }

    fun expandAndDisableHalfScreen() {
        expand()
        dialog.behavior.skipCollapsed = true
    }
}
