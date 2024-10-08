//
//  ComponentHolder.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada

class ComponentHolder<T> {
    let lifecycle: LifecycleRegistry
    let component: T
    
    init(_ stateKeeper: StateKeeper?, factory: (ComponentContext) -> T) {
        let lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        let component = factory(DefaultComponentContext(lifecycle: lifecycle, stateKeeper: stateKeeper, instanceKeeper: nil, backHandler: nil))
        self.lifecycle = lifecycle
        self.component = component
        
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
