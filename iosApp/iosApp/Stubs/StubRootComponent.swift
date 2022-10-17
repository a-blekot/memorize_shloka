//
//  StubRootComponent.swift
//  iosApp
//
//  Created by Aleksey Blekot on 11.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada

class StubRootComponent : RootComponent {

    let childStack: Value<ChildStack<AnyObject, RootComponentChild>> =
        simpleChildStack(RootComponentChildList(component: StubListComponent()))
}
