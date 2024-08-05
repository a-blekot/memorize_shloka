//
//  ObservableValue.swift
//  iosApp
//
//  Created by Aleksey Blekot on 10.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Prabhupada

public class ObservableValue<T : AnyObject> : ObservableObject {
    @Published
    var value: T
    
    private var cancellation: Cancellation?
    
    init(_ value: Value<T>) {
        self.value = value.value
        self.cancellation = value.subscribe { [weak self] value in self?.value = value }
    }
    
    deinit {
        cancellation?.cancel()
    }
}
