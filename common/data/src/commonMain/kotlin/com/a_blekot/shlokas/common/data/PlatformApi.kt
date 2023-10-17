package com.a_blekot.shlokas.common.data

interface PlatformApi {

    val hasTts: Boolean
        get() = false
    val hasInappReview: Boolean
        get() = false

    fun onEmail()
    fun onLink(link: String)
    fun onRateUs()
    fun onShareApp()
    fun onInappReview()
    fun onSelectTtsVoice()
}