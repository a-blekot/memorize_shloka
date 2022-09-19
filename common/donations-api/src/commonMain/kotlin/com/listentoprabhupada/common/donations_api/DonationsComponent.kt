package com.listentoprabhupada.common.donations_api

import com.arkivanov.decompose.value.Value

interface DonationsComponent {

    val flow: Value<DonationsState>

    fun onNext() {}
    fun onPrev() {}
}
