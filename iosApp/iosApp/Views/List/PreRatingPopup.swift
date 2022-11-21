//
//  PreRatingPopup.swift
//  iosApp
//
//  Created by Aleksey Blekot on 21.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct PreRatingPopup: View {
    @EnvironmentObject var theme: Theme
    
    let onAccept: () -> ()
    let onClose: () -> ()
    
    init(onAccept: @escaping () -> (), onClose: @escaping () -> ()) {
        self.onAccept = onAccept
        self.onClose = onClose
    }
    
    var body: some View {
        ZStack {
            VStack(alignment: .center, spacing: theme.dimens.paddingM) {

                Text(MR.strings().label_prerating_title.resolve())
                    .lineLimit(1)
                    .font(theme.headlineMedium.bold())
                    .foregroundColor(theme.colors.onPrimaryContainer)
                
                
                Text(MR.strings().label_prerating_subtitle.resolve())
                    .lineLimit(1)
                    .font(theme.headlineMedium)
                    .foregroundColor(theme.colors.onPrimaryContainer)
                
                HStack(alignment: .center, spacing: theme.dimens.paddingS) {
                    ForEach(0..<5) { _ in
                        Image(systemName: "star.fill")
                            .frame(width: theme.dimens.iconSizeL, height: theme.dimens.iconSizeL)
                            .font(theme.imageFontMedium)
                            .foregroundColor(theme.colors.primary)
                    }
                }
                
                HStack(alignment: .center, spacing: theme.dimens.paddingM) {
                    DialogButton(title: MR.strings().label_prerating_close.resolve(), isSelected: false, onClose)
                        .environmentObject(theme)
                    DialogButton(title: MR.strings().label_prerating_accept.resolve(), isSelected: true, onAccept)
                        .environmentObject(theme)
                }
            }
            .padding(.vertical, theme.dimens.paddingM)
            .padding(.horizontal, theme.dimens.horizontalScreenPadding)
            .background(theme.colors.background)
            .cornerRadius(theme.dimens.radiusM)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(theme.colors.dialogBackground)
    }
}
