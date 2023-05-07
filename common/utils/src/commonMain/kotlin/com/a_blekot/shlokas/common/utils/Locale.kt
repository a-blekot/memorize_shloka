package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.Locales.be
import com.a_blekot.shlokas.common.data.Locales.en
import com.a_blekot.shlokas.common.data.Locales.ka
import com.a_blekot.shlokas.common.data.Locales.kk
import com.a_blekot.shlokas.common.data.Locales.ru
import com.a_blekot.shlokas.common.data.Locales.uk
import dev.icerock.moko.resources.desc.StringDesc

fun checkLocale(systemLocale: String) {
    if (locale.isNotBlank()) {
        setAppLocale(locale)
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
    locale = uk
    StringDesc.localeType = StringDesc.LocaleType.Custom(uk)
}

private fun setRussian() {
    locale = ru
    StringDesc.localeType = StringDesc.LocaleType.Custom(ru)
}

private fun setEnglish() {
    locale = en
    StringDesc.localeType = StringDesc.LocaleType.Custom(en)
}