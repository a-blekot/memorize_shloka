package com.a_blekot.shlokas.common.details_impl.store

sealed interface DetailsIntent {
    data class Title(val title: String) : DetailsIntent
}
