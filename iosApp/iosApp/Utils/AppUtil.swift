//
//  AppUtil.swift
//  iosApp
//
//  Created by Aleksey Blekot on 16.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct AppUtil {
    static var isInDebugMode: Bool {
    #if SHLOKA_DEBUG
      return true
    #else
      return false
    #endif
    }
}
