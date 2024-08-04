//
//  SpeedPicker.swift
//  iosApp
//
//  Created by Aleksey Blekot on 04.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct SpeedPicker: View {
    @EnvironmentObject var theme: Theme
    @State private var selection = SettingsKt.audioSpeed
    
    private let range: ClosedRange<Float> = Audio.Speed.shared.MIN...Audio.Speed.shared.MAX

    var body: some View {
        HStack {
            Slider(
                value: $selection,
                in: range,
                step: 0.05,
                onEditingChanged: { _ in
                    SettingsKt.audioSpeed = selection
                }
            )
            .accentColor(theme.colors.primary)
            .foregroundColor(theme.colors.primary)
            .padding(.trailing, 8)

            Text("\(MR.strings().label_speed.resolve()) \(String(format: "%.2f", selection))")
                .lineLimit(1)
                .font(theme.labelSmall)
        }
        .padding()
    }
}

struct SpeedPicker_Previews: PreviewProvider {
    static var previews: some View {
        SpeedPicker()
    }
}
