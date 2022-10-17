//
//  StubPlayerBus.swift
//  iosApp
//
//  Created by Aleksey Blekot on 13.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada

class StubPlayerBus: PlayerBus {
    
    func update(task: Task) {}
    func update(feedback: PlayerFeedback) {}
    func observeFeedback(onEach: @escaping (PlayerFeedback) -> Void, scope: Kotlinx_coroutines_coreCoroutineScope?) {}
    func observeTasks(onEach: @escaping (Task) -> Void, scope: Kotlinx_coroutines_coreCoroutineScope?) {}
}
