//
//  PlayPauseButton.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct PlayPauseButton: View {
    @EnvironmentObject var theme: Theme
    
    let state: PlaybackState
    let isAutoplay: Bool
    let component: PlayerComponent
    
    var body: some View {
        
        Image(systemName: getIcon(for: state))
            .frame(width: theme.dimens.iconSizeL, height: theme.dimens.iconSizeL)
            .font(theme.imageFontMedium)
            .foregroundColor(theme.colors.onSecondaryContainer)
            .padding(theme.dimens.paddingS)
            .background(theme.colors.secondaryContainer)
            .cornerRadius(theme.dimens.radiusM)
            .onTapGesture {
                let _ = debugPrint("onTap playbackState = \(state)")
                switch(state){
                case .playing: component.forcePause()
                case .idle: if(!isAutoplay) { component.forcePlay() }
                case .noAudio: component.forcePlay()
                case .forcePaused: component.forcePlay()
                case .paused: debugPrint("paused")
                default: debugPrint("paused")
                }
            }
    }
    
    private func getIcon(for state: PlaybackState) -> String {
        switch(state){
        case .idle: return "play.fill"
        case .forcePaused: return "play.fill"
        case .noAudio: return "speaker.slash.fill"
        default: return "pause.fill"
        }
    }
}

struct PlayPauseButton_Previews: PreviewProvider {
    static var previews: some View {
        PlayPauseButton(state: .playing, isAutoplay: true, component: StubPlayerComponent())
    }
}
