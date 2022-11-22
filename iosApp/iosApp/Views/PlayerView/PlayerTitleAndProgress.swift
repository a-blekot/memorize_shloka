//
//  PlayerTitleAndProgress.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct PlayerTitleAndProgress: View {
    @EnvironmentObject var theme: Theme
    
    let state: PlayerState
    let component: PlayerComponent
    
    init(_ state: PlayerState, _ component: PlayerComponent,) {
        self.state = state
        self.component = component
    }
    
    var body: some View {
        let _ = debugPrint("!! playbackState = \(state.playbackState)")
        
        HStack(alignment: .center, spacing: theme.dimens.paddingS) {
            VStack(alignment: .center) {
                SmoothProgress(
                    current: state.currentRepeat,
                    total: state.totalRepeats,
                    duration: state.durationMs
                )
                    .frame(width: 70, height: 70)
                
                Text(MR.strings().label_repeats_counter.resolve())
                    .font(theme.labelSmall)
                    .foregroundColor(theme.colors.primary)
                    .lineLimit(1)
                    .minimumScaleFactor(0.5)
                    .padding(theme.dimens.paddingXS)
            }
            
            VStack(alignment: .center) {
                Text(state.title)
                    .font(theme.headlineLarge)
                    .foregroundColor(theme.colors.primary)
                    .lineLimit(1)
                    .minimumScaleFactor(0.5)
                
                HStack(alignment: .center, spacing: theme.dimens.paddingM) {
                    
                    Image(systemName: "arrowshape.turn.up.backward.circle")
                        .frame(width: theme.dimens.iconSizeL, height: theme.dimens.iconSizeL)
                        .font(theme.imageFontMedium)
                        .foregroundColor(theme.colors.onSecondaryContainer)
                        .padding(theme.dimens.paddingS)
                        .background(theme.colors.secondaryContainer)
                        .cornerRadius(theme.dimens.radiusM)
                        .onTapGesture {
                            if (SettingsKt.showClosePlayerDialog()) {
                                isClosePlayerDialogVisible = true
                            } else {
                                component.stop()
                            }
                        }
                    
                    PlayPauseButton(state: state.playbackState, isAutoplay: state.isAutoplay, component: component)
                }
                
            }
            .frame(maxWidth: .infinity, alignment: .center)
            
            VStack(alignment: .center) {
                let verseDuration = state.totalDurationMs / Int64(state.totalShlokasCount)
                SmoothProgress(
                    current: state.currentShlokaIndex,
                    total: state.totalShlokasCount,
                    duration: verseDuration
                )
                    .frame(width: 70, height: 70)
                
                Text(MR.strings().label_verses_counter.resolve())
                    .font(theme.labelSmall)
                    .foregroundColor(theme.colors.primary)
                    .lineLimit(1)
                    .minimumScaleFactor(0.5)
                    .padding(theme.dimens.paddingXS)
            }
        }
        .padding(theme.dimens.paddingXS)
        .padding(.top, theme.dimens.paddingXS)
    }
}

//struct PlayerTitleAndProgress_Previews: PreviewProvider {
//    static var previews: some View {
//        PlayerTitleAndProgress(state: mockPlayerState(), component: StubPlayerComponent())
//            .environmentObject(themes[0])
//    }
//}
