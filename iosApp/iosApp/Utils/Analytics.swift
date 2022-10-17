//
//  Analytics.swift
//  iosApp
//
//  Created by Aleksey Blekot on 12.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada
import FirebaseAnalytics

class AnalyticsIOs: Prabhupada.Analytics {
    func logEvent(event: AnalyticsEvent, params: [String : Any]?) {
        if AppUtil.isInDebugMode {
            NapierProxyKt.d(message: "\(event.title): \(String(describing: params))", tag: "ANALITYCS")
        } else {
            FirebaseAnalytics.Analytics.logEvent(event.title, parameters: params)
        }
    }
}
