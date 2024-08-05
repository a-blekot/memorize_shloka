package com.a_blekot.shlokas.common.utils

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.rx.observer
import io.github.aakira.napier.Napier

fun <T : Any> Store<*, T, *>.asValue(): Value<T> =
    object : Value<T>() {
        override val value: T get() = state

        override fun subscribe(observer: (T) -> Unit): Cancellation {
            val disposable = states(observer(onNext = observer))
            return Cancellation { disposable.dispose() }
        }
    }

fun Store<*, *, *>.init(instanceKeeper: InstanceKeeper) {
    Napier.d("init(instanceKeeper)", tag = "PlayerStore")
    if (instanceKeeper.get(key = this) == null) {
        instanceKeeper.put(key = this, InitInstance)
        init()
    }
}

private object InitInstance : InstanceKeeper.Instance {
    override fun onDestroy() {
        // no-op
    }
}
