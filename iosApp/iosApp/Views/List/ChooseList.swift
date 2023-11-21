//
//  ChooseList.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

extension ListPresentation: Identifiable {
    public var id: String { type.name }
}

struct ChooseList: View {
    @EnvironmentObject var theme: Theme
    
    let availableLists: [ListPresentation]
    let onSelected: (ListId) -> ()
    let onSkip: () -> ()
    
    init(_ availableLists: [ListPresentation], onSelected: @escaping (ListId) -> (), onSkip: @escaping () -> ()) {
        self.availableLists = availableLists
        self.onSelected = onSelected
        self.onSkip = onSkip
    }
    
    var body: some View {
        ZStack {
            ScrollView {
                VStack(alignment: .center, spacing: theme.dimens.paddingM) {
                    Image(systemName: "arrowshape.turn.up.backward.circle")
                        .font(theme.imageFontMedium)
                        .foregroundColor(theme.colors.onSecondaryContainer)
                        .padding(theme.dimens.paddingS)
                        .background(theme.colors.secondaryContainer)
                        .cornerRadius(theme.dimens.radiusM)
                        .onTapGesture {
                            onSkip()
                        }
                        .padding(theme.dimens.paddingS)
                        .frame(maxWidth: .infinity)
                    
                    ForEach(availableLists) { list in
                        ChooseListButton(list.title, list.isSelected)
                            .environmentObject(theme)
                            .onTapGesture {
                                let _ = debugPrint("list.type \(list.type)")
                                onSelected(list.type)
                            }
                    }
                }
            }
            .padding(.vertical, theme.dimens.paddingM)
            .padding(.horizontal, theme.dimens.horizontalScreenPadding)
            .background(theme.colors.background)
            .cornerRadius(theme.dimens.radiusM)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(theme.colors.dialogBackground)
    }
}

//struct ChooseList_Previews: PreviewProvider {
//    static var previews: some View {
//        ChooseList()
//            .environmentObject(themes[0])
//    }
//}
