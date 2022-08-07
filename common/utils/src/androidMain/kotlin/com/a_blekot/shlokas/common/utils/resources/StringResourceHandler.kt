package com.a_blekot.shlokas.common.utils.resources

import android.content.Context
import dev.icerock.moko.resources.desc.desc

class AndroidStringResourceHandler(private val context: Context) : StringResourceHandler{

    override fun resolveTitle(id: String) =
        getTitle(id).desc().toString(context)

    override fun resolveDevanagari(id: String): String {
        TODO("Not yet implemented")
    }

    override fun resolveSanskrit(id: String) =
        getSanskrit(id).desc().toString(context)

    override fun resolveWords(id: String) =
        getWords(id).desc().toString(context)

    override fun resolveTranslation(id: String) =
        getTranslation(id).desc().toString(context)

    override fun resolveDescription(id: String) =
        getDescription(id).desc().toString(context)

    override fun resolveListTitle(id: String) =
        getListTitle(id).desc().toString(context)
}