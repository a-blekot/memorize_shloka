package com.a_blekot.shlokas.common.utils.resources

import android.content.Context
import com.a_blekot.shlokas.common.data.DonationLevel
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaId
import dev.icerock.moko.resources.desc.desc

class AndroidStringResourceHandler(private val context: Context) : StringResourceHandler {

    override fun resolveTitle(id: ShlokaId) =
        versesMap[id]?.run {
            title.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveDevanagari(id: ShlokaId): String {
        TODO("Not yet implemented")
    }

    override fun resolveSanskrit(id: ShlokaId) =
        versesMap[id]?.run {
            sansk.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveWords(id: ShlokaId) =
        versesMap[id]?.run {
            words.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveTranslation(id: ShlokaId) =
        versesMap[id]?.run {
            trans.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveDescription(id: ShlokaId) =
        versesMap[id]?.run {
            descr.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveListTitle(type: ListId) =
        listsMap[type]?.run {
            title.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown list config id: ${type.id}")

    override fun resolveListShortTitle(type: ListId) =
        listsMap[type]?.run {
            shortTitle.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown list config id: ${type.id}")

    override fun resolveDonationTitle(level: DonationLevel) =
        donationsMap[level]?.run {
            desc().toString(context)
        } ?: throw IllegalArgumentException("unknown donation level: $level")
}
