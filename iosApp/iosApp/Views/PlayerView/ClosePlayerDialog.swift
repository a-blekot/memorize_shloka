//
//  ClosePlayerDialog.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada


extension StringResource {
    func resolve() -> String {
        desc().localized()
    }
}

struct ClosePlayerDialog: View {
    @EnvironmentObject var theme: Theme
    
    let onStop: () -> ()
    let onContinue: () -> ()
    
    @State var doNotShowAgain: Bool = false
    
    init(onStop: @escaping () -> (), onContinue: @escaping () -> ()) {
        self.onStop = onStop
        self.onContinue = onContinue
    }
    
    var body: some View {
        ZStack {
            VStack(alignment: .center, spacing: theme.dimens.paddingM) {
                
                Text(MR.strings().label_close_player_title.resolve())
                    .font(theme.headlineMedium.bold())
                    .foregroundColor(theme.colors.onPrimaryContainer)
                
                Text(MR.strings().label_close_player_subtitle.resolve())
                    .font(theme.titleLarge)
                    .foregroundColor(theme.colors.onPrimaryContainer)
                
                HStack(alignment: .center, spacing: theme.dimens.paddingM) {
                    StandartCheckBox(doNotShowAgain, theme.colors.primary) { isSelected in
                        doNotShowAgain = isSelected
                        SettingsKt.showClosePlayerDialog = !isSelected
                    }
                    
                    Text(MR.strings().label_do_not_show_again.resolve())
                        .font(theme.titleMedium)
                        .lineLimit(1)
                }
                .foregroundColor(theme.colors.primary)
                
                HStack(alignment: .center, spacing: theme.dimens.paddingM) {
                    DialogButton(title: MR.strings().label_close_player_stop.resolve(), isSelected: false, onStop)
                        .environmentObject(theme)
                    DialogButton(title: MR.strings().label_close_player_continue.resolve(), isSelected: true, onContinue)
                        .environmentObject(theme)
                }
            }
            .frame(maxWidth: .infinity)
            .padding(theme.dimens.paddingM)
            .background(theme.colors.background)
            .cornerRadius(theme.dimens.radiusM)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(theme.colors.dialogBackground)
        .zIndex(2)
    }
}

struct ClosePlayerDialog_Previews: PreviewProvider {
    static var previews: some View {
        ClosePlayerDialog(onStop: {}, onContinue: {})
            .environmentObject(themes[0])
    }
}
