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

    let current: Week
    let onChanged: (Week) -> ()
    
    init(current: Week, onChanged: @escaping (Week) -> ()) {
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
                Image(systemName: pickImage(for: Week.first))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_one_line.resolve())
                    .font(theme.titleLarge)
            }
            .foregroundColor(theme.colors.primary)
            .onTapGesture {
                onChanged(Week.first)
            }
            
            HStack {
                Image(systemName: pickImage(for: Week.second))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_two_lines.resolve())
                    .font(theme.titleLarge)
            }
            .foregroundColor(theme.colors.primary)
            .onTapGesture {
                onChanged(Week.second)
            }
            
            HStack {
                Image(systemName: pickImage(for: Week.third))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_four_lines.resolve())
                    .font(theme.titleLarge)
            }
            .foregroundColor(theme.colors.primary)
            .onTapGesture {
                onChanged(Week.third)
            }
        }
    }
    
    private func pickImage(for week: Week) -> String {
        current == week ? "smallcircle.filled.circle" : "circle"
    }
}

struct WeekPicker_Previews: PreviewProvider {
    static var previews: some View {
        WeekPicker(current: Week.second) { week in }
    }
}
