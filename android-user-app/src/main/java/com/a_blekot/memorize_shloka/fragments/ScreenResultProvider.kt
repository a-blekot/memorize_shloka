package com.a_blekot.memorize_shloka.fragments

/** Abstraction to split navigation implementation and screen UI */
fun interface ScreenResultProvider <T> {

    fun observe(consumer : (T) -> Unit)

}