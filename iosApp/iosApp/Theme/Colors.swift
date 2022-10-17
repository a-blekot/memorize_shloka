//
//  Colors.swift
//  iosApp
//
//  Created by Aleksey Blekot on 13.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

extension Color {
    init(_ hex: Int, opacity: Double = 1.0) {
        let red = Double((hex & 0xff0000) >> 16) / 255.0
        let green = Double((hex & 0xff00) >> 8) / 255.0
        let blue = Double((hex & 0xff) >> 0) / 255.0
        self.init(.sRGB, red: red, green: green, blue: blue, opacity: opacity)
    }
}

protocol ColorScheme {
    
    var background: Color { get }
    var onBackground: Color { get }
    var dialogBackground: Color { get }
    
    var primary: Color { get }
    var onPrimary: Color { get }
    var primaryContainer: Color { get }
    var onPrimaryContainer: Color { get }
    
    var secondary: Color { get }
    var onSecondary: Color { get }
    var secondaryContainer: Color { get }
    var onSecondaryContainer: Color { get }
    
    var errorContainer: Color { get }
    var onErrorContainer: Color { get }
}

struct LightColors: ColorScheme {
    
    let background = Color(0xFFFFFBFF)
    let onBackground = Color(0xFF1E1B1A)
    let dialogBackground = Color(0x881E1B1A).opacity(0.5)
    
    
    let primary = Color(0xFF7A564A)
    let onPrimary = Color(0xFFFFFFFF)
    let primaryContainer = Color(0xFFFFDBD0)
    let onPrimaryContainer = Color(0xFF2E140C)
    
    let secondary = Color(0xFF695C58)
    let onSecondary = Color(0xFFFFFFFF)
    let secondaryContainer = Color(0xFFF1DFD9)
    let onSecondaryContainer = Color(0xFF231917)
    
    let errorContainer = Color(0xFFFFDAD6)
    let onErrorContainer = Color(0xFF410002)
}

struct DarkColors: ColorScheme {
    
    let background = Color(0xFF1E1B1A)
    let onBackground = Color(0xFFE8E1DF)
    let dialogBackground = Color(0x88E8E1DF).opacity(0.5)
    
    let primary = Color(0xFFEBBCAD)
    let onPrimary = Color(0xFF47291F)
    let primaryContainer = Color(0xFF603F34)
    let onPrimaryContainer = Color(0xFFFFDBD0)

    let secondary = Color(0xFFD4C3BE)
    let onSecondary = Color(0xFF392E2B)
    let secondaryContainer = Color(0xFF504441)
    let onSecondaryContainer = Color(0xFFF1DFD9)

    let errorContainer = Color(0xFF93000A)
    let onErrorContainer = Color(0xFFFFB4AB)
}
