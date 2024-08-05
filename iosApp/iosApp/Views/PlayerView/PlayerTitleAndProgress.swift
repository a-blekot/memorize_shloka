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
    
    private let state: PlayerState
    private let component: PlayerComponent
    private let onCopyTapped: () -> ()
    @Binding private var isClosePlayerDialogVisible: Bool
    
    init(_ state: PlayerState, _ component: PlayerComponent, _ isClosePlayerDialogVisible: Binding<Bool>, _ onCopyTapped: @escaping () -> ()) {
        self.state = state
        self.component = component
        self._isClosePlayerDialogVisible = isClosePlayerDialogVisible
        self.onCopyTapped = onCopyTapped
    }
    
    var body: some View {
        let _ = debugPrint("ChildView  ## \(state.playbackState) \(state.currentRepeat)")
        
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
                HStack(alignment: .center, spacing: theme.dimens.paddingS) {
                    Text(state.title)
                        .font(theme.headlineLarge)
                        .foregroundColor(theme.colors.primary)
                        .lineLimit(1)
                        .minimumScaleFactor(0.5)
                    
                    Image(systemName: "doc.on.clipboard")
                        .frame(width: theme.dimens.iconSizeM, height: theme.dimens.iconSizeM)
                        .font(theme.imageFontSmall)
                        .foregroundColor(theme.colors.onSecondaryContainer)
                        .padding(theme.dimens.paddingS)
                        .background(theme.colors.secondaryContainer)
                        .cornerRadius(theme.dimens.radiusM)
                }
                .onTapGesture {
                    onCopyTapped()
                }
                
                
                HStack(alignment: .center, spacing: theme.dimens.paddingM) {
                    
                    Image(systemName: "arrowshape.turn.up.backward.circle")
                        .frame(width: theme.dimens.iconSizeM, height: theme.dimens.iconSizeM)
                        .font(theme.imageFontMedium)
                        .foregroundColor(theme.colors.onSecondaryContainer)
                        .padding(theme.dimens.paddingS)
                        .background(theme.colors.secondaryContainer)
                        .cornerRadius(theme.dimens.radiusM)
                        .onTapGesture {
                            if (SettingsKt.showClosePlayerDialog) {
                                isClosePlayerDialogVisible = true
                            } else {
                                component.stop()
                            }
                        }
                    
                    PlayerButton(isNext: false, component: component)
                    
                    PlayPauseButton(state: state.playbackState, isAutoplay: state.isAutoplay, hasAudio: state.hasAudio, component: component)
                    
                    PlayerButton(isNext: true, component: component)
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

extension PlayerState {
    func copyAll() -> String {
        let combinedText = """
        \(title)
        
        \(sanskrit)
        
        \(translation)
        """
        return combinedText.noHtmlTags()
    }
}

extension String {
    func noHtmlTags() -> String {
        // Replace <br> with new lines
        var result = self.replacingOccurrences(of: "<br>", with: "\n")
        
        // Regular expression pattern to match HTML tags
        let regexPattern = "<(?:!|/?[a-zA-Z]+).*?/?>"
        
        do {
            let regex = try NSRegularExpression(pattern: regexPattern, options: [])
            let range = NSRange(location: 0, length: result.utf16.count)
            result = regex.stringByReplacingMatches(in: result, options: [], range: range, withTemplate: "")
        } catch {
            print("Failed to create regex: \(error)")
        }
        
        return result
    }
}



//struct PlayerTitleAndProgress_Previews: PreviewProvider {
//    static var previews: some View {
//        PlayerTitleAndProgress(state: mockPlayerState(), component: StubPlayerComponent())
//            .environmentObject(themes[0])
//    }
//}
