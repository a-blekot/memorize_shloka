package com.a_blekot.shlokas.common.details_api

sealed interface DetailsOutput {
    object SaveChanges: DetailsOutput
}