//
//  Theme.swift
//  iosApp
//
//  Created by Aleksey Blekot on 20.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

enum ThemeName {
    case lightTheme
    case darkTheme
}

enum Fonts{
    case headlineLarge
    case headlineMedium
    case headlineSmall
    
    case titleLarge
    case titleMedium
    case titleSmall
    
    case bodyLarge
    case labelSmall
    
    case imageFontLarge
    case imageFontMedium
    case imageFontSmall
}

class Theme:ObservableObject {
    @Published var colors: ColorScheme
    @Published var dimens: Dimens
    
    @Published var headlineLarge:Font
    @Published var headlineMedium:Font
    @Published var headlineSmall:Font
    @Published var titleLarge:Font
    @Published var titleMedium:Font
    @Published var titleSmall:Font
    @Published var bodyLarge:Font
    @Published var labelSmall:Font
    @Published var imageFontLarge:Font
    @Published var imageFontMedium:Font
    @Published var imageFontSmall:Font
    
    init(
        colors:ColorScheme,
        dimens:Dimens,
        headlineLarge:Font,
        headlineMedium:Font,
        headlineSmall:Font,
        titleLarge:Font,
        titleMedium:Font,
        titleSmall:Font,
        bodyLarge:Font,
        labelSmall:Font,
        imageFontLarge:Font,
        imageFontMedium:Font,
        imageFontSmall:Font
    ){
        self.colors = colors
        self.dimens = dimens
        
        self.headlineLarge = headlineLarge
        self.headlineMedium = headlineMedium
        self.headlineSmall = headlineSmall
        self.titleLarge = titleLarge
        self.titleMedium = titleMedium
        self.titleSmall = titleSmall
        self.bodyLarge = bodyLarge
        self.labelSmall = labelSmall
        self.imageFontLarge = imageFontLarge
        self.imageFontMedium = imageFontMedium
        self.imageFontSmall = imageFontSmall
    }
}

func getFont(name:Fonts) -> Font{
    switch(name){
    case .headlineLarge: return Font.system(size: 32)
    case .headlineMedium: return Font.system(size: 28)
    case .headlineSmall: return Font.system(size: 24)
        
    case .titleLarge: return Font.system(size: 22)
    case .titleMedium: return Font.system(size: 16)
    case .titleSmall: return Font.system(size: 14)
        
    case .bodyLarge: return Font.system(size: 16)
    case .labelSmall: return Font.system(size: 11)
    case .imageFontLarge: return Font.system(size: 48)
    case .imageFontMedium: return Font.system(size: 36)
    case .imageFontSmall: return Font.system(size: 24)
    }
}

var themes: [Theme] = [
    //default = lightTheme
    Theme(
        colors: LightColors(),
        dimens: Dimens(),
        headlineLarge: getFont(name: .headlineLarge),
        headlineMedium: getFont(name: .headlineMedium),
        headlineSmall: getFont(name: .headlineSmall),
        titleLarge: getFont(name: .titleLarge),
        titleMedium: getFont(name: .titleMedium),
        titleSmall: getFont(name: .titleSmall),
        bodyLarge: getFont(name: .bodyLarge),
        labelSmall: getFont(name: .labelSmall),
        imageFontLarge: getFont(name: .imageFontLarge),
        imageFontMedium: getFont(name: .imageFontMedium),
        imageFontSmall: getFont(name: .imageFontSmall)
    ),
    Theme(
        colors: DarkColors(),
        dimens: Dimens(),
        headlineLarge: getFont(name: .headlineLarge),
        headlineMedium: getFont(name: .headlineMedium),
        headlineSmall: getFont(name: .headlineSmall),
        titleLarge: getFont(name: .titleLarge),
        titleMedium: getFont(name: .titleMedium),
        titleSmall: getFont(name: .titleSmall),
        bodyLarge: getFont(name: .bodyLarge),
        labelSmall: getFont(name: .labelSmall),
        imageFontLarge: getFont(name: .imageFontLarge),
        imageFontMedium: getFont(name: .imageFontMedium),
        imageFontSmall: getFont(name: .imageFontSmall)
    )
]
