package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.DonationLevel

interface StringResourceHandler {
    fun resolveTitle(id: String): String
    fun resolveDevanagari(id: String): String
    fun resolveSanskrit(id: String): String
    fun resolveWords(id: String): String
    fun resolveTranslation(id: String): String
    fun resolveDescription(id: String): String
    fun resolveListTitle(id: String): String
    fun resolveListShortTitle(id: String): String
    fun resolveDonationTitle(level: DonationLevel): String
}