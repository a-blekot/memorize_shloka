package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.Locales.be
import com.a_blekot.shlokas.common.data.Locales.en
import com.a_blekot.shlokas.common.data.Locales.ka
import com.a_blekot.shlokas.common.data.Locales.kk
import com.a_blekot.shlokas.common.data.Locales.ru
import com.a_blekot.shlokas.common.data.Locales.uk
import dev.icerock.moko.resources.desc.StringDesc

fun checkLocale(systemLocale: String) {
    if (getLocale().isNotBlank()) {
        setAppLocale(getLocale())
        return // means that locale already selected by user
    }

    setAppLocale(systemLocale)
}

fun setAppLocale(locale: String) =
    when(locale) {
        uk -> setUkrainian()
        ru, be, ka, kk -> setRussian()
        else -> setEnglish()
    }

private fun setUkrainian() {
    saveLocale(uk)
    StringDesc.localeType = StringDesc.LocaleType.Custom(uk)
}

private fun setRussian() {
    saveLocale(ru)
    StringDesc.localeType = StringDesc.LocaleType.Custom(ru)
}

private fun setEnglish() {
    saveLocale(en)
    StringDesc.localeType = StringDesc.LocaleType.Custom(en)
}