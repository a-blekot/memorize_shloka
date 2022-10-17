//
//  AnyFlow.swift
//  iosApp
//
//  Created by Aleksey Blekot on 13.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Combine
import Foundation
import Prabhupada

typealias OnEach<Output> = (Output) -> Void
typealias OnCompletion<Failure> = (Failure?) -> Void

//typealias OnCollect<Output, Failure> = (@escaping OnEach<Output>, @escaping OnCompletion<Failure>) -> Prabhupada.Cancellable
//
///**
// Creates a `Publisher` that collects output from a flow wrapper function emitting values from an underlying
// instance of `Flow<T>`.
// */
//func collect<Output, Failure>(_ onCollect: @escaping OnCollect<Output, Failure>) -> Publishers.Flow<Output, Failure> {
//    return Publishers.Flow(onCollect: onCollect)
//}
