package com.a_blekot.memorize_shloka.inapp

import android.app.Activity
import com.a_blekot.memorize_shloka.MainApp.Companion.app
import com.a_blekot.shlokas.common.utils.onInappReviewShown
import com.google.android.play.core.review.ReviewManagerFactory
import io.github.aakira.napier.Napier

fun showInappReview(activity: Activity, onNext: () -> Unit = {}) {
    val reviewManager = ReviewManagerFactory.create(app)
    val requestReviewFlow = reviewManager.requestReviewFlow()
    requestReviewFlow.addOnCompleteListener { request ->
        if (request.isSuccessful) {
            val reviewInfo = request.result
            reviewManager.launchReviewFlow(activity, reviewInfo).apply {
                addOnCompleteListener {
                    onInappReviewShown()
                    onNext()
                }
            }
        } else {
            request.exception?.let { Napier.e("InappReview", it, "requestReviewFlow is not successful") }
            onNext()
        }
    }
}
