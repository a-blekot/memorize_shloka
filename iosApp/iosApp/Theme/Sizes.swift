//
//  Sizes.swift
//  iosApp
//
//  Created by Aleksey Blekot on 13.04.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import CoreGraphics

let SIZE_PLAY_BUTTON = CGFloat(36)
let SIZE_FAVORITE_BUTTON = CGFloat(28)

let CONTEXT_MENU_BTN_W = CGFloat(10)
let CONTEXT_MENU_BTN_H = CGFloat(30)

let SIZE_PLAYER_CONTROL_BUTTON = CGFloat(15)

enum Sizes: CGFloat {
    case playerControl = 25
    case playerControlBig = 35
}

func size(_ size: Sizes) -> CGFloat {
    return size.rawValue
}

struct Dimens {
    let iconSizeM = CGFloat(24)
    let iconSizeL = CGFloat(36)
    let iconSizeXL = CGFloat(48)

    let radiusS = CGFloat(4)
    let radiusM = CGFloat(8)

    let borderS = CGFloat(2)

    let paddingZero = CGFloat(0)
    let paddingXS = CGFloat(4)
    let paddingS = CGFloat(8)
    let paddingM = CGFloat(16)

    let horizontalScreenPadding = CGFloat(8)

    let buttonHeight = CGFloat(48)
}
