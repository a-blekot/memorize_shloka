package com.a_blekot.memorize_shloka.bottom_sheet.controller

class BottomSheetSuspendableControllerImpl(
    private val controller: BottomSheetClassicController,
) : BottomSheetSuspendableController {

    override suspend fun expand() {
        controller.expand()
    }

    override suspend fun expandAndDisableHalfScreen() {
        controller.expandAndDisableHalfScreen()
    }
}
