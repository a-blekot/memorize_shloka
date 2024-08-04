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
    
    private let component: PlayerComponent
    
    @State var isClosePlayerDialogVisible = false
    
    @GestureState private var dragOffset = CGSize.zero
    
    init(_ component: PlayerComponent) {
        self.component = component
        self.state = ObservableValue(component.flow)
    }
    
    var body: some View {
        let state = state.value
        let _ = debugPrint("PlayerView ## \(state.playbackState) \(state.currentRepeat)")
        
        ZStack {
            VStack(alignment: .center, spacing: theme.dimens.paddingS) {
                PlayerTitleAndProgress(state, component, $isClosePlayerDialogVisible)
                    .environmentObject(theme)
                    .padding(.horizontal, theme.dimens.horizontalScreenPadding)
                
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
                        
                        FoldableView(
                            title: "Synonyms",
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
                            title: "Translation",
                            textColor: theme.colors.onSecondaryContainer,
                            bgColor: theme.colors.secondaryContainer
                        ) {
                            Text(state.translation)
                                .font(theme.titleLarge)
                                .foregroundColor(theme.colors.onSecondaryContainer)
                                .padding(.horizontal, theme.dimens.paddingS)
                                .frame(maxWidth: .infinity, alignment: .leading)
                        }
                    }
                }
                .padding(.horizontal, CGFloat(24))
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(theme.colors.background)
            
            .offset(x: dragOffset.width)
            .gesture(
                DragGesture()
                    .updating($dragOffset) { value, state, _ in
                        state = value.translation
                    }
                    .onEnded { value in
                        let velocity = value.predictedEndTranslation.width / abs(value.translation.width)
                        
                        if velocity > 4 {
                            component.next()
                        } else if velocity < -4 {
                            component.prev()
                        }
                    }
            )
            
            if (isClosePlayerDialogVisible) {
                ClosePlayerDialog(
                    onStop: { component.stop() },
                    onContinue: {
                        isClosePlayerDialogVisible = false
                    }
                )
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct PlayerView_Previews: PreviewProvider {
    static var previews: some View {
        PlayerView(StubPlayerComponent())
            .environmentObject(themes[0])
    }
}
