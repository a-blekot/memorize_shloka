//
//  DialogButton.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct DialogButton: View {
    @EnvironmentObject var theme: Theme
    
    let title: String
    let isSelected: Bool
    let onClick: () -> ()
    
    init(title: String, isSelected: Bool, _ onClick: @escaping () -> ()) {
        self.title = title
        self.isSelected = isSelected
        self.onClick = onClick
    }
    
    var body: some View {
        Text(title)
            .font(theme.headlineSmall)
            .lineLimit(1)
            .foregroundColor(theme.colors.onPrimaryContainer)
            .minimumScaleFactor(0.5)
            .frame(maxWidth: .infinity, minHeight: theme.dimens.buttonHeight, alignment: .center)
            .background(theme.colors.secondaryContainer.opacity(isSelected ? 1 : 0))
            .overlay(
                RoundedRectangle(cornerRadius: theme.dimens.radiusM)
                    .stroke(theme.colors.primary, lineWidth: theme.dimens.borderS)
            )
            .onTapGesture {
                onClick()
            }
    }
}
