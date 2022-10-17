//
//  ListItemView.swift
//  iosApp
//
//  Created by Aleksey Blekot on 13.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada


struct ListItemView: View {
    
    @EnvironmentObject var theme: Theme
    
    let config: ShlokaConfig
    let component : ListComponent
    
    var body: some View {
        HStack(alignment: .center, spacing: theme.dimens.paddingS) {
            
            VStack(alignment: .leading, spacing: 2.0) {
                Text(config.shloka.title)
                    .font(theme.titleLarge.bold())
                    .allowsTightening(true)
                    .lineLimit(1)
                    .truncationMode(.tail)
                    .foregroundColor(theme.colors.onPrimaryContainer)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Text(component.resolveDescription(id: config.shloka.id))
                    .font(theme.titleLarge.italic())
                    .lineLimit(1)
                    .truncationMode(.tail)
                    .foregroundColor(theme.colors.onPrimaryContainer)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            
            VStack(alignment: .leading, spacing: 2.0) {
                
                if (!config.shloka.hasAudio) {
                    Image(systemName: "speaker.slash.fill")
                        .imageScale(.large)
                }
                
                StandartCheckBox(config.isSelected, theme.colors.onPrimaryContainer) { isSelected in
                    component.select(id: config.shloka.id, isSelected: isSelected)
                }
                
                
            }
            .foregroundColor(theme.colors.onPrimaryContainer)
        }
        .padding(theme.dimens.paddingXS)
        .padding(.vertical, theme.dimens.paddingXS)
        .background(theme.colors.primaryContainer.opacity(0.3))
        .cornerRadius(theme.dimens.radiusS)
        .buttonStyle(.plain)
        .onTapGesture {
            component.play(config: config)
        }
    }
}

struct ListItemView_Previews: PreviewProvider {
    static var previews: some View {
        ListItemView(config: mockShlokaConfig(), component: StubListComponent())
            .environmentObject(themes[0])
    }
}
