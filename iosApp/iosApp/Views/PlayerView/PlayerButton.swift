//
//  PlayerButton.swift
//  iosApp
//
//  Created by Aleksey Blekot on 04.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct PlayerButton: View {
    @EnvironmentObject var theme: Theme
    
    let isNext: Bool
    let component: PlayerComponent
    
    var body: some View {
        Button(action: {
            if isNext {
                component.next()
            } else {
                component.prev()
            }
        }) {
            Image(systemName: isNext ? "forward.end.fill" : "backward.end.fill")
                .frame(width: theme.dimens.iconSizeL, height: theme.dimens.iconSizeL)
                .font(theme.imageFontMedium)
                .foregroundColor(theme.colors.onSecondaryContainer)
                .padding(theme.dimens.paddingS)
                .background(theme.colors.secondaryContainer)
                .cornerRadius(theme.dimens.radiusM)
        }
    }
}
