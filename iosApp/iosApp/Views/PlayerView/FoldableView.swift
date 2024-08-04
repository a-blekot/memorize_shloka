//
//  FoldableView.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct FoldableView<Content: View>: View {
    
    @EnvironmentObject var theme: Theme
    @State var isExpanded = false
    
    let title: String
    let textColor: Color
    let bgColor: Color
    @ViewBuilder var content: Content
    
    var body: some View {

        VStack(alignment: .leading, spacing: theme.dimens.paddingXS) {
            HStack(alignment: .center, spacing: theme.dimens.paddingXS) {
                Image(systemName: isExpanded ? "chevron.down.circle" : "chevron.up.circle")
                    .imageScale(.large)
                    .padding(.horizontal, theme.dimens.paddingS)
                
                Text(title)
                    .font(theme.titleSmall)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .foregroundColor(textColor)
            .onTapGesture {
                isExpanded = !isExpanded
            }
            
            if (isExpanded) {
                content
            }
        }
        .padding(.vertical, theme.dimens.paddingS)
        .background(bgColor)
        .cornerRadius(theme.dimens.paddingS)
    }
}

struct FoldableView_Previews: PreviewProvider {
    static var previews: some View {
        FoldableView(
            title: "Synonyms",
            textColor: themes[0].colors.onSecondaryContainer,
            bgColor: themes[0].colors.secondaryContainer
        ) {
            Text("I'm text")
        }
            .environmentObject(themes[0])
    }
}
