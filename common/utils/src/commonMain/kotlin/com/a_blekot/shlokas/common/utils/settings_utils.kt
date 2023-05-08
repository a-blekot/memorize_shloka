package com.a_blekot.shlokas.common.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Settings.int(
    key: String? = null,
    defaultValue: Int,
    minValue: Int,
    maxValue: Int
): ReadWriteProperty<Any?, Int> =
    IntDelegate(
        settings = this,
        key = key,
        defaultValue = defaultValue,
        minValue = minValue,
        maxValue = maxValue,
    )

fun Settings.long(
    key: String? = null,
    defaultValue: Long,
    minValue: Long,
    maxValue: Long
): ReadWriteProperty<Any?, Long> =
    LongDelegate(
        settings = this,
        key = key,
        defaultValue = defaultValue,
        minValue = minValue,
        maxValue = maxValue,
    )

fun Settings.float(
    key: String? = null,
    defaultValue: Float,
    minValue: Float,
    maxValue: Float
): ReadWriteProperty<Any?, Float> =
    FloatDelegate(
        settings = this,
        key = key,
        defaultValue = defaultValue,
        minValue = minValue,
        maxValue = maxValue,
    )

private class IntDelegate(
    private val settings: Settings,
    key: String?,
    private val defaultValue: Int,
    private val minValue: Int,
    private val maxValue: Int,
) : OptionalKeyDelegate<Int>(key) {
    override fun getValue(key: String): Int =
        settings[key, defaultValue].coerceIn(minValue, maxValue)

    override fun setValue(key: String, value: Int) {
        settings[key] = value.coerceIn(minValue, maxValue)
    }
}

private class LongDelegate(
    private val settings: Settings,
    key: String?,
    private val defaultValue: Long,
    private val minValue: Long,
    private val maxValue: Long,
) : OptionalKeyDelegate<Long>(key) {
    override fun getValue(key: String): Long =
        settings[key, defaultValue].coerceIn(minValue, maxValue)

    override fun setValue(key: String, value: Long) {
        settings[key] = value.coerceIn(minValue, maxValue)
    }
}

private class FloatDelegate(
    private val settings: Settings,
    key: String?,
    private val defaultValue: Float,
    private val minValue: Float,
    private val maxValue: Float,
) : OptionalKeyDelegate<Float>(key) {
    override fun getValue(key: String): Float =
        settings[key, defaultValue].coerceIn(minValue, maxValue)

    override fun setValue(key: String, value: Float) {
        settings[key] = value.coerceIn(minValue, maxValue)
    }
}

private abstract class OptionalKeyDelegate<T>(private val key: String?) : ReadWriteProperty<Any?, T> {

    abstract fun getValue(key: String): T
    abstract fun setValue(key: String, value: T)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = getValue(key ?: property.name)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setValue(key ?: property.name, value)
    }
}