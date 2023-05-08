package com.a_blekot.shlokas.common.player_impl.store


inline fun <T> List<T>.firstAfter(after: Int, predicate: (T) -> Boolean): Int {
    if (after + 1 !in indices) return -1
    for (i in after + 1..lastIndex) {
        if (predicate(get(i)))
            return i
    }
    return -1
}

inline fun <T> List<T>.firstBefore(before: Int, predicate: (T) -> Boolean): Int {
    if (before - 1 !in indices) return -1
    for (i in (0 until before).reversed()) {
        if (predicate(get(i)))
            return i
    }
    return -1
}