//
//  SmoothProgress.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Prabhupada

struct SmoothProgress: View {
    @EnvironmentObject var theme: Theme
    
    let current: Int32
    let total: Int32
    let duration: Int64
    
    init(current: Int32, total: Int32, duration: Int64) {
        self.current = current
        self.total = total
        self.duration = duration
    }
    
    var body: some View {
        ZStack {
            Circle()
                .stroke(lineWidth: theme.dimens.borderS)
                .opacity(0.3)
                .foregroundColor(theme.colors.secondaryContainer)
            
            Circle()
                .trim(from: 0.0, to: min(CGFloat(current)/CGFloat(total), 1.0))
                .stroke(style: StrokeStyle(lineWidth: theme.dimens.borderS, lineCap: .round, lineJoin: .round))
                .foregroundColor(theme.colors.primary)
                .rotationEffect(Angle(degrees: 270.0))
                .animation(.linear(duration: Double(duration)/1000))
            
            VStack(alignment: .center, spacing: 0) {
                Text("\(current)")
                    .font(theme.titleLarge)
                    .foregroundColor(theme.colors.primary)
                    .allowsTightening(true)
                    .minimumScaleFactor(0.5)
                    .lineLimit(1)
                    .frame(maxHeight: .infinity)
                
                Divider()
                    .foregroundColor(theme.colors.secondaryContainer)
                    .padding(.horizontal, theme.dimens.paddingXS)
                    .frame(width: .infinity, height: theme.dimens.borderS, alignment: .center)
                
                
                Text("\(total)")
                    .font(theme.titleLarge)
                    .foregroundColor(theme.colors.primary)
                    .allowsTightening(true)
                    .minimumScaleFactor(0.5)
                    .lineLimit(1)
                    .frame(maxHeight: .infinity)
            }
            
            

        }
        .aspectRatio(1, contentMode: .fill)
    }
}

//struct SmoothProgress_Previews: PreviewProvider {
//    static var previews: some View {
//        SmoothProgress(3, 10, 1000)
//    }
//}
