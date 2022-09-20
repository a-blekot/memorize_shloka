package com.a_blekot.shlokas.common.utils.resources

import android.content.Context
import com.a_blekot.shlokas.common.data.DonationLevel
import dev.icerock.moko.resources.desc.desc

class AndroidStringResourceHandler(private val context: Context) : StringResourceHandler {

    override fun resolveTitle(id: String) =
        versesMap[id]?.run {
            title.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveDevanagari(id: String): String {
        TODO("Not yet implemented")
    }

    override fun resolveSanskrit(id: String) =
        versesMap[id]?.run {
            sansk.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveWords(id: String) =
        versesMap[id]?.run {
            words.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveTranslation(id: String) =
        versesMap[id]?.run {
            trans.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveDescription(id: String) =
        versesMap[id]?.run {
            descr.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    override fun resolveListTitle(id: String) =
        listsMap[id]?.run {
            title.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown list config id: $id")

    override fun resolveListShortTitle(id: String) =
        listsMap[id]?.run {
            shortTitle.desc().toString(context)
        } ?: throw IllegalArgumentException("unknown list config id: $id")

    override fun resolveDonationTitle(level: DonationLevel) =
        donationsMap[level]?.run {
            desc().toString(context)
        } ?: throw IllegalArgumentException("unknown donation level: $level")
}
