package com.a_blekot.memorize_shloka.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner


/**
 * https://developer.android.com/guide/fragments/communicate#fragment-result
 *
 * 1. Wrap result into typed Parcelable to avoid mess with different result types if it has multiple fields
 * 2. Use single result key to avoid mess with different consumers if typed result has multiple fields
 *
 * Complexity of types is caused by the case when screen A waits for A.Result, screen B waits for B.Result and screen C provides C.Result
 *
 * All A.Result, B.Result and C.Result mostly contain same fields, but we have no need for A and B screens to depend on C screen just for declaration of C.Result
 *
 * So we use param mapper to bind result provider screen and result consumer screens
 * */
private fun <ProviderResult : Parcelable, ConsumerResult : Any> createScreenResultProvider(
    resultKey: String,
    mapper: (ProviderResult) -> ConsumerResult,
    fragmentManager: FragmentManager,
    lifecycleOwner: LifecycleOwner,
): ScreenResultProvider<ConsumerResult> {
    // Improvements are welcome
    var latestResult: ConsumerResult? = null
    var consumer: (ConsumerResult) -> Unit = {
        latestResult = it
    }

    fragmentManager.setFragmentResultListener(resultKey, lifecycleOwner) { key, bundle ->
        val result = bundle.getParcelable<ProviderResult?>(resultKey)!!
        val value = mapper.invoke(result)
        consumer.invoke(value)
    }
    return ScreenResultProvider { actualConsumer ->
        consumer = actualConsumer
        latestResult?.let {
            actualConsumer.invoke(it)
            latestResult = null
        }
    }
}

fun <ProviderResult : Parcelable, ConsumerResult : Any> Fragment.createScreenResultProvider(
    resultKey: String,
    mapper: (ProviderResult) -> ConsumerResult,
    fromDialog: Boolean = true,
): ScreenResultProvider<ConsumerResult> {
    val fragmentManager = if (fromDialog) {
        childFragmentManager
    } else {
        parentFragmentManager
    }
    return createScreenResultProvider(resultKey, mapper, fragmentManager, this)
}

fun <ProviderResult : Parcelable, ConsumerResult : Any> FragmentActivity.createScreenResultProvider(
    resultKey: String,
    mapper: (ProviderResult) -> ConsumerResult,
): ScreenResultProvider<ConsumerResult> {
    return createScreenResultProvider(resultKey, mapper, supportFragmentManager, this)
}

fun <ProviderResult : Parcelable> Fragment.createScreenResultProvider(
    resultKey: String,
    fromDialog: Boolean = true,
): ScreenResultProvider<ProviderResult> {
    return createScreenResultProvider<ProviderResult, ProviderResult>(
        resultKey,
        mapper = { it },
        fromDialog
    )
}

fun <ProviderResult : Parcelable> FragmentActivity.createScreenResultProvider(
    resultKey: String,
): ScreenResultProvider<ProviderResult> {
    return createScreenResultProvider<ProviderResult, ProviderResult>(resultKey, mapper = { it })
}


/**
 * https://developer.android.com/guide/fragments/communicate#fragment-result
 *
 * 1. Wrap result into typed Parcelable to avoid mess with different result types if it has multiple fields
 * 2. Use single result key to avoid mess with different consumers if typed result has multiple fields
 * */
fun Fragment.setScreenResult(resultKey: String, result: Parcelable) {
    val bundle = Bundle().apply {
        putParcelable(resultKey, result)
    }
    setFragmentResult(resultKey, bundle)
}