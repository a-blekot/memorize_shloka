//
//  IAPManager.swift
//  iosApp
//
//  Created by Aleksey Blekot on 02.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import StoreKit

class IAPManager: NSObject {
    static let shared = IAPManager()
    
    var products = [String: SKProduct]()
    
    enum Product: String, CaseIterable {
        case diamond_100
        case diamond_200
        case diamond_500
    }
    
    public func fetchProducts() {
        let request = SKProductsRequest(
            productIdentifiers: Set(Product.allCases.compactMap({ $0.rawValue }))
        )
        request.delegate = self
        request.start()
    }
}

extension IAPManager: SKProductsRequestDelegate {
    func productsRequest(_ request: SKProductsRequest,
                         didReceive response: SKProductsResponse) {
        
        print("products = \(response.products)")
        print("invalidProductIdentifiers = \(response.invalidProductIdentifiers)")
        
        guard !response.products.isEmpty else {
            print("Found 0 products")
            return
        }
    }

    func request(_ request: SKRequest, didFailWithError error: Error) {
        print("Failed to load products with error:\n \(error)")
    }
}
