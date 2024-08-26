//
//  WeekPicker.swift
//  iosApp
//
//  Created by Aleksey Blekot on 15.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct WeekPicker: View {
    @EnvironmentObject var theme: Theme

    let current: RepeatMode
    let onChanged: (RepeatMode) -> ()
    
    init(current: RepeatMode, onChanged: @escaping (RepeatMode) -> ()) {
        self.current = current
        self.onChanged = onChanged
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(MR.strings().label_repeat_mode.resolve())
                .lineLimit(1)
                .font(theme.titleLarge.bold())
                .frame(maxWidth: .infinity)
            
            HStack {
                Image(systemName: pickImage(for: RepeatMode.oneLine))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_one_line.resolve())
                    .font(theme.titleLarge)
            }
            .foregroundColor(theme.colors.primary)
            .onTapGesture {
                onChanged(RepeatMode.oneLine)
            }
            
            HStack {
                Image(systemName: pickImage(for: RepeatMode.twoLines))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_two_lines.resolve())
                    .font(theme.titleLarge)
            }
            .foregroundColor(theme.colors.primary)
            .onTapGesture {
                onChanged(RepeatMode.twoLines)
            }
            
            HStack {
                Image(systemName: pickImage(for: RepeatMode.fourLines))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_four_lines.resolve())
                    .font(theme.titleLarge)
            }
            .foregroundColor(theme.colors.primary)
            .onTapGesture {
                onChanged(RepeatMode.fourLines)
            }
            
            HStack {
                Image(systemName: pickImage(for: RepeatMode.quickLearn))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_quick_learn.resolve())
                    .font(theme.titleLarge)
            }
            .foregroundColor(theme.colors.primary)
            .onTapGesture {
                onChanged(RepeatMode.quickLearn)
            }
        }
    }
    
    private func pickImage(for repeatMode: RepeatMode) -> String {
        current == repeatMode ? "smallcircle.filled.circle" : "circle"
    }
}

struct WeekPicker_Previews: PreviewProvider {
    static var previews: some View {
        WeekPicker(current: RepeatMode.twoLines) { repeatMode in }
    }
}
