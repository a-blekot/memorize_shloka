package com.a_blekot.memorize_shloka.bottom_sheet.controller

interface BottomSheetSuspendableController {

    /**
     * Expand from half screen to full screen.
     * User will be able to collapse BottomSheet back to half screen
     * */
    suspend fun expand()

    /**
     * Expand from half screen to full screen.
     * User gesture 'swipe down' will close the BottomSheet without intermediate half screen state
     * */
    suspend fun expandAndDisableHalfScreen()
}
