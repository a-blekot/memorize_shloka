package com.a_blekot.shlokas.common.data

const val YOU_TUBE_SHLOKA_SMARANAM = "https://www.youtube.com/@shloka-smaranam"

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