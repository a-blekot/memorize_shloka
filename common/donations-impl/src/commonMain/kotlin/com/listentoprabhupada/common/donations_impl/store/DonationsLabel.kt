package com.listentoprabhupada.common.donations_impl.store

sealed interface DonationsLabel {
    data class Message(val text: String) : DonationsLabel
}
