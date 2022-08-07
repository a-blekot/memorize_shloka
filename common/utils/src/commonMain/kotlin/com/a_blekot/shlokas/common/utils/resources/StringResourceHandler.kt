package com.a_blekot.shlokas.common.utils.resources

interface StringResourceHandler {
    fun resolveTitle(id: String): String
    fun resolveDevanagari(id: String): String
    fun resolveSanskrit(id: String): String
    fun resolveWords(id: String): String
    fun resolveTranslation(id: String): String
    fun resolveDescription(id: String): String
    fun resolveListTitle(id: String): String
}