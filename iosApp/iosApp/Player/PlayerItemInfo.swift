//
//  PlayerItemInfo.swift
//  iosApp
//
//  Created by Aleksey Blekot on 21.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct PlayerItemInfo: Equatable {
    let id: String
    let title: String
    let description: String
    let duration: Int64
    
    public static func == (lhs: PlayerItemInfo, rhs: PlayerItemInfo) -> Bool {
        return lhs.id == rhs.id
    }
}
