package com.a_blekot.memorize_shloka.utils

import android.app.Activity
import com.a_blekot.shlokas.common.utils.billing.BillingHelper
import com.a_blekot.shlokas.common.utils.billing.DonationProduct
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.Purchase.PurchaseState

class BillingHelperAndroid(private val activity: Activity) : BillingHelper {

    private var productDetailsList = emptyList<ProductDetails>()

    private val billingClient: BillingClient

    private val purchaseListener = PurchasesUpdatedListener { billingResult, purchasesList ->
        if (billingResult.isOk) {
            if (purchasesList == null) {
                onPurchaseSuccess(null)
            } else {
                purchasesList.forEach(::processPurchase)
            }
        }
    }

    override val availableDonations
        get() = productDetailsList.map {
            DonationProduct(it.productId, it.title, it.oneTimePurchaseOfferDetails?.formattedPrice)
        }

    init {
        billingClient = BillingClient.newBuilder(activity)
            .enablePendingPurchases()
            .setListener(purchaseListener)
            .build()

        getProductDetails()
    }

    override fun purchase(donation: DonationProduct) {
        donation.toBillingFlowParams()?.let { billingFlowParams ->
            activity.runOnUiThread {
                onConnected {
                    billingClient.launchBillingFlow(activity, billingFlowParams)
                }
            }
        }
    }

    override fun checkUnconsumedPurchases() {
        onConnected {
            billingClient.queryPurchasesAsync(inappQueryPurchasesParams) { billingResult, purchasesList ->
                if (billingResult.isOk) {
                    purchasesList.forEach {
                        processPurchase(it)
                    }
                }
            }
        }
    }

    private fun getProductDetails() {
        onConnected {
            billingClient.queryProductDetailsAsync(getQueryProductDetailsParams()) { billingResult, productDetailsList ->
                if (billingResult.isOk) {
                    this.productDetailsList = productDetailsList
                }
            }
        }
    }

    private fun processPurchase(purchase: Purchase) {
        if (purchase.isPurchased) {
            purchase.products.forEach { id ->
                availableDonations
                    .firstOrNull { it.productId == id }
                    ?.let { onPurchaseSuccess(it) }
            }

            consumePurchase(purchase) { billingResult, purchaseToken ->
                if (!billingResult.isOk) {
                    //implement retry logic or try to consume again in onResume()
                }
            }
        }
    }

    private fun consumePurchase(purchase: Purchase, callback: ConsumeResponseListener) {
        onConnected {
            billingClient.consumeAsync(purchase.consumeParams) { billingResult, purchaseToken ->
                callback.onConsumeResponse(billingResult, purchaseToken)
            }
        }
    }

    private fun onConnected(block: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.isOk) {
                    block()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    private fun getQueryProductDetailsParams() =
        QueryProductDetailsParams.newBuilder()
            .setProductList(getProducts())
            .build()

    private fun getProducts() =
        listOf(
            getProduct("donate_1_usd"),
            getProduct("donate_2_usd"),
            getProduct("donate_3_usd"),
            getProduct("donate_5_usd"),
            getProduct("donate_10_usd"),
            getProduct("donate_25_usd"),
        )

    private fun getProduct(id: String) =
        QueryProductDetailsParams.Product
            .newBuilder()
            .setProductId(id)
            .setProductType(ProductType.INAPP)
            .build()

    private fun DonationProduct.toBillingFlowParams() =
        toProductDetailsParamsList()?.let { list ->
            BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(list)
                .build()
        }

    private fun DonationProduct.toProductDetailsParamsList() =
        toProductDetailsParams()?.let { productDetails -> listOf(productDetails) }

    private fun DonationProduct.toProductDetailsParams() =
        productDetailsList.firstOrNull { it.productId == productId }?.let {
            ProductDetailsParams.newBuilder()
                .setProductDetails(it)
                .build()
        }

    private val BillingResult.isOk
        get() = responseCode == BillingResponseCode.OK

    private val Purchase.isPurchased
        get() = purchaseState == PurchaseState.PURCHASED

    private val Purchase.consumeParams
        get() = ConsumeParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()

    private val inappQueryPurchasesParams
        get() = QueryPurchasesParams.newBuilder()
            .setProductType(ProductType.INAPP)
            .build()
}