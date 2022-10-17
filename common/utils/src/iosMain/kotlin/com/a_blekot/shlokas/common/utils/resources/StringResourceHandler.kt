package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.DonationLevel
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaId
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

private fun StringResource.desc() =
    StringDesc.Resource(this)

class IOsStringResourceHandler : StringResourceHandler {

    @Throws(IllegalArgumentException::class)
    override fun resolveTitle(id: ShlokaId) =
        versesMap[id]?.run {
            title.desc().localized()
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    @Throws(IllegalArgumentException::class)
    override fun resolveDevanagari(id: ShlokaId): String {
        TODO("Not yet implemented")
    }

    @Throws(IllegalArgumentException::class)
    override fun resolveSanskrit(id: ShlokaId) =
        versesMap[id]?.run {
            sansk.desc().localized()
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    @Throws(IllegalArgumentException::class)
    override fun resolveWords(id: ShlokaId) =
        versesMap[id]?.run {
            words.desc().localized()
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    @Throws(IllegalArgumentException::class)
    override fun resolveTranslation(id: ShlokaId) =
        versesMap[id]?.run {
            trans.desc().localized()
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    @Throws(IllegalArgumentException::class)
    override fun resolveDescription(id: ShlokaId) =
        versesMap[id]?.run {
            descr.desc().localized()
        } ?: throw IllegalArgumentException("unknown shloka id: $id")

    @Throws(IllegalArgumentException::class)
    override fun resolveListTitle(type: ListId) =
        listsMap[type]?.run {
            title.desc().localized()
        } ?: throw IllegalArgumentException("unknown list config id: ${type.id}")

    @Throws(IllegalArgumentException::class)
    override fun resolveListShortTitle(type: ListId) =
        listsMap[type]?.run {
            shortTitle.desc().localized()
        } ?: throw IllegalArgumentException("unknown list config id: ${type.id}")

    @Throws(IllegalArgumentException::class)
    override fun resolveDonationTitle(level: DonationLevel) =
        donationsMap[level]?.run {
            desc().localized()
        } ?: throw IllegalArgumentException("unknown donation level: $level")
}
