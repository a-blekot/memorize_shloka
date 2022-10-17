//
//  RootView.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada
import AVFoundation

struct RootView: View {
    
    @EnvironmentObject var theme: Theme
    
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, RootComponentChild>>
    
    private var activeChild: RootComponentChild { childStack.value.active.instance }
    
    private let component: RootComponent
    
    init(_ component: RootComponent) {
        self.component = component
        self.childStack = ObservableValue(component.childStack)
    }

    var body: some View {
        VStack(spacing: 0) {
            HStack(alignment: .bottom, spacing: theme.dimens.paddingS) {}
            
            ChildView(child: activeChild)
                .frame(maxHeight: .infinity)
        }
        
    }
}

private struct ChildView: View {
    let child: RootComponentChild
    
    var body: some View {
        switch child {
        case let list as RootComponentChildList:
            ListView(list.component)
            
        case let player as RootComponentChildPlayer:
            PlayerView(player.component)
            
        case let settings as RootComponentChildSettings:
            SettingsView(settings.component)
            
//        case let filters as RootComponentChildFilters:
//            FiltersView(filters.component)
//
//            //        case let downloads as RootComponentChildDownloads:
//            //            FavoritesFeatureView(favorites.component)
//
//        case let favorites as RootComponentChildFavorites:
//            FavoritesView(favorites.component)
//
        default: EmptyView() // fatalError("Unexpected router state \(child)")
        }
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        RootView(StubRootComponent())
            .environmentObject(themes[0])
    }
}
