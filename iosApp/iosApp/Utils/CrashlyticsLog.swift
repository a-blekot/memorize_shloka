//
//  CrashlyticsLog.swift
//  iosApp
//
//  Created by Aleksey Blekot on 16.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada
import FirebaseCrashlytics


class CrashlyticsAntilog: Antilog {
    
    override func performLog(priority: LogLevel, tag: String?, throwable: KotlinThrowable?, message: String?) {
        if (priority == LogLevel.error || priority == LogLevel.debug) {
            if let message = message {
                if let tag = tag {
                    Crashlytics.crashlytics().setCustomValue(message, forKey: tag)
                } else {
                    Crashlytics.crashlytics().log(message)
                }
            }
            
            if let throwable = throwable {
                Crashlytics.crashlytics().record(exceptionModel: ExceptionModel(name: throwable.description(), reason: throwable.message ?? ""))
            }
        }
    }
}
