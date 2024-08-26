//
//  PlayerView.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct PlayerView: View {
    @EnvironmentObject var theme: Theme
    
    @ObservedObject
    private var state: ObservableValue<PlayerState>
    
    @State  private var showRepeatModeDropdown =  false
    
    private let component: PlayerComponent
    
    @State var isClosePlayerDialogVisible = false
    @State private var isToastShowing = false
    
    @GestureState private var dragOffset = CGSize.zero
    
    static let SWIPE_VELOCITY: CGFloat = 2.5
    
    init(_ component: PlayerComponent) {
        self.component = component
        self.state = ObservableValue(component.flow)
    }
    
    var body: some View {
        let state = state.value
        let _ = debugPrint("PlayerView ## \(state.playbackState) \(state.currentRepeat)")
        
        ZStack(alignment: Alignment(horizontal: .trailing, vertical: .bottom)) {
            VStack(alignment: .center, spacing: theme.dimens.paddingS) {
                PlayerTitleAndProgress(state, component, $isClosePlayerDialogVisible) {
                    copyAllText(state)
                }
                .environmentObject(theme)
                //                 .padding(.horizontal, theme.dimens.horizontalScreenPadding)
                
                ScrollView {
                    VStack {
                        Text(state.sanskrit.toMarkdown())
                            .font(theme.headlineSmall)
                            .allowsTightening(true)
                            .multilineTextAlignment(.center)
                            .minimumScaleFactor(0.8)
                            .frame(maxWidth: .infinity, alignment: .center)
                            .padding(.horizontal, theme.dimens.paddingXS)
                            .foregroundColor(theme.colors.primary)
                        
                        ToastView(message: MR.strings().label_text_copied.resolve(), isShowing: $isToastShowing)
                        
                        FoldableView(
                            title: MR.strings().label_words.resolve(),
                            textColor: theme.colors.onSecondaryContainer,
                            bgColor: theme.colors.secondaryContainer
                        ) {
                            Text(state.words.toMarkdown())
                                .font(theme.titleLarge)
                                .padding(theme.dimens.paddingS)
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .foregroundColor(theme.colors.onSecondaryContainer)
                        }
                        
                        FoldableView(
                            title: MR.strings().label_translation.resolve(),
                            textColor: theme.colors.onSecondaryContainer,
                            bgColor: theme.colors.secondaryContainer
                        ) {
                            Text(state.translation)
                                .font(theme.titleLarge)
                                .foregroundColor(theme.colors.onSecondaryContainer)
                                .padding(.horizontal, theme.dimens.paddingS)
                                .frame(maxWidth: .infinity, alignment: .leading)
                        }
                        
                        Spacer()
                            .frame(height: 44)
                    }
                }
                //                 .padding(.horizontal, CGFloat(16))
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .padding(.horizontal, theme.dimens.horizontalScreenPadding)
            .padding(.vertical, theme.dimens.paddingXS)
            .background(theme.colors.background)
            .gesture(
                DragGesture()
                    .updating($dragOffset) { value, state, _ in
                        state = value.translation
                    }
                    .onEnded { value in
                        let velocity = value.predictedEndTranslation.width / abs(value.translation.width)
                        
                        if velocity > PlayerView.SWIPE_VELOCITY {
                            component.next()
                        } else if velocity < -PlayerView.SWIPE_VELOCITY {
                            component.prev()
                        }
                    }
            )
            
            RepeatModeDropDown2(currentMode: state.repeatMode, showDropdown: $showRepeatModeDropdown) { repeatMode in
                component.repeatModeChanged(newMode: repeatMode)
            }
            .environmentObject(theme)
            .padding(.trailing, theme.dimens.horizontalScreenPadding)
            .padding(.bottom, theme.dimens.paddingXS)
            
            if (isClosePlayerDialogVisible) {
                ClosePlayerDialog(
                    onStop: { component.stop() },
                    onContinue: {
                        isClosePlayerDialogVisible = false
                    }
                )
            }
        }
        .onTapGesture {
            withAnimation {
                showRepeatModeDropdown =  false
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
    
    private func copyAllText(_ state: PlayerState) {
        isToastShowing = true
        
        let pasteboard = UIPasteboard.general
        pasteboard.string = state.copyAll()
    }
}

struct PlayerView_Previews: PreviewProvider {
    static var previews: some View {
        PlayerView(StubPlayerComponent())
            .environmentObject(themes[0])
    }
}
