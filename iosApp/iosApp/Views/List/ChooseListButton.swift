//
//  ChooseListButton.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct ChooseListButton: View {
    @EnvironmentObject var theme: Theme
    
    let title: String
    let isSelected: Bool
    
    init(_ title: String, _ isSelected: Bool) {
        self.title = title
        self.isSelected = isSelected
    }
    
    var body: some View {
        Text(title)
            .font(theme.titleLarge)
            .lineLimit(1)
            .foregroundColor(theme.colors.onPrimaryContainer)
            .minimumScaleFactor(0.5)
            .frame(maxWidth: .infinity, minHeight: theme.dimens.buttonHeight, alignment: .center)
            .background(theme.colors.secondaryContainer.opacity(isSelected ? 1 : 0))
            .overlay(
                RoundedRectangle(cornerRadius: theme.dimens.radiusM)
                    .stroke(theme.colors.primary, lineWidth: theme.dimens.borderS)
            )
    }
}

struct ChooseListButton_Previews: PreviewProvider {
    static var previews: some View {
        ChooseListButton("SB First canto", true)
            .environmentObject(themes[0])
    }
}
