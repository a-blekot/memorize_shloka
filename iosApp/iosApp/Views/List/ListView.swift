//
//  ListView.swift
//  iosApp
//
//  Created by Aleksey Blekot on 13.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

extension ShlokaConfig: Identifiable {
    public var id: String { shloka.id.name }
}

struct ListView: View {
    @EnvironmentObject var theme: Theme
    
    @ObservedObject
    private var state: ObservableValue<ListState>
    
    let component: ListComponent
    
    @State private var menuIsVisible = false
    
    init(_ component: ListComponent) {
        self.component = component
        self.state = ObservableValue(component.flow)
    }
    
    var body: some View {
        let state = state.value
        
        ZStack {
            VStack(alignment: .center, spacing: theme.dimens.paddingXS) {
                HStack(alignment: .center, spacing: theme.dimens.paddingXS) {
                    Image(systemName: "books.vertical.fill")
                        .font(theme.imageFontLarge)
                        .frame(maxWidth: .infinity)
                        .onTapGesture {
                            menuIsVisible = true
                        }
                    
                    Image(systemName: "play.circle.fill")
                        .font(theme.imageFontLarge)
                        .frame(maxWidth: .infinity)
                        .onTapGesture {
                            component.play()
                        }
                    
                    Image(systemName: "paperplane.fill")
                        .font(theme.imageFontLarge)
                        .frame(maxWidth: .infinity)
                        .onTapGesture {
                            component.shareApp()
                        }
                    
                    Image(systemName: "dollarsign.circle.fill")
                        .font(theme.imageFontLarge)
                        .frame(maxWidth: .infinity)
                        .onTapGesture {
                            //component.donations()
                        }
                        .opacity(0.5)
                    
                    Image(systemName: "gearshape.fill")
                        .font(theme.imageFontLarge)
                        .frame(maxWidth: .infinity)
                        .onTapGesture {
                            component.settings()
                        }
                }
                .padding(.horizontal, theme.dimens.horizontalScreenPadding)
                .foregroundColor(theme.colors.primary)
                
                Text(state.config.title)
                    .font(theme.headlineLarge)
                    .lineLimit(2)
                    .allowsTightening(true)
                    .multilineTextAlignment(.center)
                    .foregroundColor(theme.colors.primary)
                    .minimumScaleFactor(0.5)
                    .frame(maxWidth: .infinity, alignment: .center)
                
                ScrollView {
                    VStack(alignment: .center, spacing: theme.dimens.paddingXS) {
                        ForEach(state.config.list) { shlokaConfig in
                            ListItemView(config: shlokaConfig, component: component)
                                .environmentObject(theme)
                        }
                    }
                }
                .padding(.horizontal, theme.dimens.horizontalScreenPadding)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(theme.colors.background)
            
            
            if (menuIsVisible && !state.availableLists.isEmpty) {
                ChooseList(
                    state.availableLists,
                    onSelected: { listId in
                        menuIsVisible = false
                        component.setList(type: listId)
                    },
                    onSkip: {
                        menuIsVisible = false
                    }
                )
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct ListView_Previews: PreviewProvider {
    static var previews: some View {
        ListView(StubListComponent())
            .environmentObject(themes[0])
    }
}
