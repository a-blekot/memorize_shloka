//
//  StandartCheckBox.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct StandartCheckBox: View {
    
    @EnvironmentObject var theme: Theme
    
    let isSelected: Bool
    let color: Color
    let onChanged: (Bool) -> ()
    
    init(_ isSelected: Bool, _ color: Color, onChanged: @escaping (Bool) -> ()) {
        self.isSelected = isSelected
        self.color = color
        self.onChanged = onChanged
    }
    
    var body: some View {
        Image(systemName: isSelected ? "checkmark.square.fill" : "square")
            .font(theme.imageFontSmall)
            .onTapGesture { onChanged(!isSelected) }
    }
}

struct StandartCheckBox_Previews: PreviewProvider {
    static var previews: some View {
        StandartCheckBox(true, Color(0xFF7A564A)) { isSelected in
        }
    }
}
