package com.listentoprabhupada.common.donations_api

sealed interface DonationsOutput {
    data class Message(val text: String) : DonationsOutput
}
