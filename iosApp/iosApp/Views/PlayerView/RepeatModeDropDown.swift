//
//  RepeatModeDropDown.swift
//  iosApp
//
//  Created by Aleksey Blekot on 26.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct RepeatModeDropDown: View {
    @EnvironmentObject var theme: Theme
    
    @State private var expanded = false
    
    let currentMode: RepeatMode
    let onRepeatModeChanged: (RepeatMode) -> Void
    
    init(currentMode: RepeatMode, onRepeatModeChanged: @escaping (RepeatMode) -> ()) {
        self.currentMode = currentMode
        self.onRepeatModeChanged = onRepeatModeChanged
    }
    
    var body: some View {
        VStack {
            Button(action: {
                expanded.toggle()
            }) {
                Text(currentMode.title)
                    .font(theme.titleSmall)
                    .padding()
                    .background(
                        RoundedRectangle(cornerRadius: 5)
                            .stroke(theme.colors.onSecondaryContainer, lineWidth: 1)
                            .background(theme.colors.secondaryContainer)
                    )
            }
            
            if expanded {
                VStack(spacing: 0) {
                    ForEach(RepeatMode.entries.indices, id: \.self) { index in
                        let repeatMode = RepeatMode.entries[index]
                        
                        Button(action: {
                            expanded = false
                            onRepeatModeChanged(repeatMode)
                        }) {
                            HStack {
                                Text(repeatMode.title)
                                    .font(theme.titleSmall)
                                
                                Spacer()
                                
                                if repeatMode == currentMode {
                                    Image(systemName: "checkmark")
                                }
                            }
                        }
                        .padding()
                        
                        if index != RepeatMode.entries.count - 1 {
                            Divider()
                                .background(theme.colors.onSecondaryContainer)
                        }
                    }
                }
                .cornerRadius(5)
                .overlay(
                    RoundedRectangle(cornerRadius: 5)
                        .stroke(theme.colors.onSecondaryContainer, lineWidth: 1)
                )
            }
        }
        .foregroundColor(theme.colors.onSecondaryContainer)
        .background(theme.colors.secondaryContainer)
    }
}


extension RepeatMode {
    
    var id: String { self.title }
    
    var title: String {
        switch self {
        case .oneLine:
            return MR.strings().label_one_line.resolve()
        case .twoLines:
            return MR.strings().label_two_lines.resolve()
        case .fourLines:
            return MR.strings().label_four_lines.resolve()
        case .quickLearn:
            return MR.strings().label_quick_learn.resolve()
        default:
            return "UNKNOWN repeat mode"
        }
    }
}

