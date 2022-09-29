package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.DonationLevel
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaId

interface StringResourceHandler {
    fun resolveTitle(id: ShlokaId): String
    fun resolveDevanagari(id: ShlokaId): String
    fun resolveSanskrit(id: ShlokaId): String
    fun resolveWords(id: ShlokaId): String
    fun resolveTranslation(id: ShlokaId): String
    fun resolveDescription(id: ShlokaId): String
    fun resolveListTitle(type: ListId): String
    fun resolveListShortTitle(type: ListId): String
    fun resolveDonationTitle(level: DonationLevel): String
}