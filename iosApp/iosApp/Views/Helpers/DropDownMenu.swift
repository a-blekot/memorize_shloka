//
//  DropDownMenu.swift
//  iosApp
//
//  Created by Aleksey Blekot on 26.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct RepeatModeDropDown2: View {
    
    @EnvironmentObject var theme: Theme
    
    private let menuWdith: CGFloat  =  250
    private let buttonHeight: CGFloat  =  40
    
    private let currentMode: RepeatMode
    private let onRepeatModeChanged: (RepeatMode) -> Void
    @Binding private var showDropdown: Bool
    
    init(currentMode: RepeatMode,
         showDropdown: Binding<Bool>,
         onRepeatModeChanged: @escaping (RepeatMode) -> ()) {
        self.currentMode = currentMode
        self.onRepeatModeChanged = onRepeatModeChanged
        self._showDropdown = showDropdown
    }
    
    var body: some  View {
        
        VStack {
            
            VStack(spacing: 0) {
                if (showDropdown) {
                    let scrollViewHeight: CGFloat  = buttonHeight*CGFloat(RepeatMode.entries.count)
                    ScrollView {
                        LazyVStack(spacing: 0) {
                            ForEach(0..<RepeatMode.entries.count, id: \.self) { index in
                                let repeatMode = RepeatMode.entries[index]
                                
                                Button(action: {
                                    withAnimation {
                                        showDropdown.toggle()
                                        if (repeatMode != currentMode) {
                                            onRepeatModeChanged(repeatMode)
                                        }
                                    }
                                    
                                }, label: {
                                    HStack {
                                        Text(repeatMode.title)
                                            .font(theme.titleSmall)
                                        Spacer()
                                        if (repeatMode == currentMode) {
                                            Image(systemName: "checkmark.circle.fill")
                                        }
                                    }
                                    
                                })
                                .padding(.horizontal, 20)
                                .frame(width: menuWdith, height: buttonHeight, alignment: .leading)
                                
                                if index != RepeatMode.entries.count - 1 {
                                    Divider()
                                        .background(theme.colors.onSecondaryContainer)
                                }
                            }
                        }
                    }
                    .frame(height: scrollViewHeight)
                    
                } else {
                    // selected item
                    Button(action: {
                        withAnimation {
                            showDropdown.toggle()
                        }
                    }, label: {
                        HStack(spacing: nil) {
                            Text(MR.strings().label_repeat_mode.resolve())
                                .font(theme.titleSmall)
                            Spacer()
                            Image(systemName: "chevron.up")
                                .rotationEffect(.degrees((showDropdown ?  -180 : 0)))
                        }
                    })
                    .padding(.horizontal, 20)
                    .frame(width: menuWdith, height: buttonHeight, alignment: .leading)
                }
            }
            .foregroundStyle(theme.colors.onSecondaryContainer)
            .background(
                RoundedRectangle(cornerRadius: 16)
                    .stroke(theme.colors.onSecondaryContainer, lineWidth: 1)
                    .background(theme.colors.secondaryContainer)
            )
            .cornerRadius(16)
        }
        .frame(width: menuWdith, height: buttonHeight, alignment: .bottom)
        .zIndex(1)
    }
}
