package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.DonationLevel
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaId

interface StringResourceHandler {
    @Throws(IllegalArgumentException::class)
    fun resolveTitle(id: ShlokaId): String

    @Throws(IllegalArgumentException::class)
    fun resolveDevanagari(id: ShlokaId): String

    @Throws(IllegalArgumentException::class)
    fun resolveSanskrit(id: ShlokaId): String

    @Throws(IllegalArgumentException::class)
    fun resolveWords(id: ShlokaId): String

    @Throws(IllegalArgumentException::class)
    fun resolveTranslation(id: ShlokaId): String

    @Throws(IllegalArgumentException::class)
    fun resolveDescription(id: ShlokaId): String

    @Throws(IllegalArgumentException::class)
    fun resolveListTitle(type: ListId): String

    @Throws(IllegalArgumentException::class)
    fun resolveListShortTitle(type: ListId): String

    @Throws(IllegalArgumentException::class)
    fun resolveDonationTitle(level: DonationLevel): String
}