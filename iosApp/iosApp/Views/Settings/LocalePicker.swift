//
//  LocalePicker.swift
//  iosApp
//
//  Created by Aleksey Blekot on 15.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada


struct LocalePicker: View {
    @EnvironmentObject var theme: Theme

    let current: String
    let onChanged: (String) -> ()
    
    init(current: String, onChanged: @escaping (String) -> ()) {
        self.current = current
        self.onChanged = onChanged
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(MR.strings().label_select_locale.resolve())
                .lineLimit(1)
                .font(theme.titleLarge.bold())
                .frame(maxWidth: .infinity)
            
            HStack {
                Image(systemName: pickImage(for: Locales().en))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_locale_en.resolve())
                    .font(theme.titleLarge)
            }
            .onTapGesture {
                onChanged(Locales().en)
            }
            
            HStack {
                Image(systemName: pickImage(for: Locales().uk))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_locale_uk.resolve())
                    .font(theme.titleLarge)
            }
            .onTapGesture {
                onChanged(Locales().uk)
            }
            
            HStack {
                Image(systemName: pickImage(for: Locales().ru))
                    .font(theme.imageFontSmall)
                
                Text(MR.strings().label_locale_ru.resolve())
                    .font(theme.titleLarge)
            }
            .onTapGesture {
                onChanged(Locales().ru)
            }
        }
    }
    
    private func pickImage(for locale: String) -> String {
        current == locale ? "smallcircle.filled.circle" : "circle"
    }
}

struct LocalePicker_Previews: PreviewProvider {
    static var previews: some View {
        LocalePicker(current: "en") { isSelected in }
    }
}
