//
//  Purchases.swift
//  iosApp
//
//  Created by Aleksey Blekot on 02.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import StoreKit
import Prabhupada

typealias RequestProductsResult = Result<[SKProduct], Error>
typealias PurchaseProductResult = Result<String, Error>

typealias RequestProductsCompletion = (RequestProductsResult) -> Void
typealias PurchaseProductCompletion = (PurchaseProductResult) -> Void

class BillingHelperIOs: BillingHelperDefault {
    init() {
        super.init(dispatchers: DispatcherProviderImplKt.dispatchers())
        Purchases.default.billingHelper = self
        requestProducts()
    }
    
    override func purchase(donation: Donation) {
        Purchases.default.purchaseProduct(productId: donation.donationLevel.name) { [weak self] result in
            switch result {
            case .success(let productId): self?.onSuccessPurchase(productId: productId)
            case .failure: break
            }
        }
    }

    private func requestProducts() {
        Purchases.default.initialize { result in
            switch result {
            case .success(let products):
                
                let availableDonations = products.compactMap { product in
                    self.getDonation(from: product)
                }
                
                let sorted = availableDonations.sorted(by: { $0.priceAmountMicros < $1.priceAmountMicros })
                self.updateDonations(list: sorted)
                break
            case .failure:
                break
            }
        }
    }
    
    private func getDonation(from product: SKProduct) -> Donation? {
        guard let donationLevel = DonationLevelKt.getDonationLevel(productId: product.productIdentifier) else {
                return nil
            }

        return Donation(
            donationLevel: donationLevel,
            formattedPrice: product.localizedPrice,
            priceAmountMicros: Int64(truncating: product.price),
            title: ""
        )
    }
}

class Purchases: NSObject {
    static let `default` = Purchases()
    
    enum Product: String, CaseIterable {
        case donate_1_usd
        case donate_2_usd
        case donate_3_usd
        case donate_5_usd
        case donate_10_usd
        case donate_25_usd
    }
    
    var billingHelper: BillingHelperIOs? = nil

    private var products: [String: SKProduct]?
    private var productRequest: SKProductsRequest?

    func initialize(completion: @escaping RequestProductsCompletion) {
        requestProducts(completion: completion)
    }

    private var productsRequestCallbacks = [RequestProductsCompletion]()
    fileprivate var productPurchaseCallback: ((PurchaseProductResult) -> Void)?

    func purchaseProduct(productId: String, completion: @escaping (PurchaseProductResult) -> Void) {
        // 1:
        guard productPurchaseCallback == nil else {
            completion(.failure(PurchasesError.purchaseInProgress))
            return
        }
        // 2:
        guard let product = products?[productId] else {
            completion(.failure(PurchasesError.productNotFound))
            return
        }

        productPurchaseCallback = completion

        // 3:
        let payment = SKPayment(product: product)
        SKPaymentQueue.default().add(payment)
    }

    public func restorePurchases(completion: @escaping (PurchaseProductResult) -> Void) {
        guard productPurchaseCallback == nil else {
            completion(.failure(PurchasesError.purchaseInProgress))
            return
        }
        productPurchaseCallback = completion
        // 4:
        SKPaymentQueue.default().restoreCompletedTransactions()
    }
    
    private func requestProducts(completion: @escaping RequestProductsCompletion) {
        guard productsRequestCallbacks.isEmpty else {
            productsRequestCallbacks.append(completion)
            return
        }

        productsRequestCallbacks.append(completion)

        let productRequest = SKProductsRequest(
            productIdentifiers: Set(Product.allCases.compactMap({ $0.rawValue }))
        )
        productRequest.delegate = self
        productRequest.start()

        self.productRequest = productRequest
    }
}

extension Purchases: SKProductsRequestDelegate {
    func productsRequest(_ request: SKProductsRequest, didReceive response: SKProductsResponse) {
        guard !response.products.isEmpty else {
            print("Found 0 products")

            productsRequestCallbacks.forEach { $0(.success(response.products)) }
            productsRequestCallbacks.removeAll()
            return
        }

        var products = [String: SKProduct]()
        for skProduct in response.products {
            print("Found product: \(skProduct.productIdentifier)")
            products[skProduct.productIdentifier] = skProduct
        }

        self.products = products

        productsRequestCallbacks.forEach { $0(.success(response.products)) }
        productsRequestCallbacks.removeAll()
    }

    func request(_ request: SKRequest, didFailWithError error: Error) {
        print("Failed to load products with error:\n \(error)")

        productsRequestCallbacks.forEach { $0(.failure(error)) }
        productsRequestCallbacks.removeAll()
    }
}

extension Purchases: SKPaymentTransactionObserver {
    func paymentQueue(_ queue: SKPaymentQueue, updatedTransactions transactions: [SKPaymentTransaction]) {
        // 1:
        for transaction in transactions {
            switch transaction.transactionState {
            // 2:
            case .purchased, .restored:
                if finishTransaction(transaction) {
                    let productId = transaction.payment.productIdentifier
                    SKPaymentQueue.default().finishTransaction(transaction)
                    productPurchaseCallback?(.success(productId))
                } else {
                    productPurchaseCallback?(.failure(PurchasesError.unknown))
                }
            // 3:
            case .failed:
                productPurchaseCallback?(.failure(transaction.error ?? PurchasesError.unknown))
                SKPaymentQueue.default().finishTransaction(transaction)
            default:
                break
            }
        }

        productPurchaseCallback = nil
    }
}

extension Purchases {
    // 4:
    func finishTransaction(_ transaction: SKPaymentTransaction) -> Bool {
        let productId = transaction.payment.productIdentifier
        print("Product \(productId) successfully purchased")
        return true
    }
}

extension SKProduct {
    var localizedPrice: String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = priceLocale
        return formatter.string(from: price)!
    }

    var title: String? {
        switch productIdentifier {
        case "barcode_month_subscription":
            return "Monthly Subscription"
        case "barcode_year_subscription":
            return "Annual Subscription"
        default:
            return nil
        }
    }
}

enum PurchasesError: Error {
    case purchaseInProgress
    case productNotFound
    case unknown
}
