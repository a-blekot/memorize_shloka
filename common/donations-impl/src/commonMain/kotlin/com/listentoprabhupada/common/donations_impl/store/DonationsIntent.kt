package com.listentoprabhupada.common.donations_impl.store

sealed interface DonationsIntent {
    object Next : DonationsIntent
    object Prev : DonationsIntent
}
