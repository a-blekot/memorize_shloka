//
//  PeriodicTimeObserver.swift
//  iosApp
//
//  Created by Aleksey Blekot on 21.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import AVFoundation
import Prabhupada

private let ONE_SECOND = CMTime(value: 1, timescale: 1)

class PeriodicTimeObserver {
    
    private let avPlayer: AVPlayer
    private let player: Player
    private let playerBus: PlayerBus
    
    private var timeObserverToken: Any?
    
    init(avPlayer: AVPlayer, player: Player, playerBus: PlayerBus) {
        self.avPlayer = avPlayer
        self.player = player
        self.playerBus = playerBus
        addPeriodicTimeObserver()
    }
    
    deinit {
        removePeriodicTimeObserver()
    }
    
    private func update(timeMs: Int64) {
//         playerBus.update(
//             state: player.currentState(timeMs: timeMs)
//         )
    }
    
    private func addPeriodicTimeObserver() {
        print("IOs addPeriodicTimeObserver -> ")
        timeObserverToken = avPlayer.addPeriodicTimeObserver(forInterval: ONE_SECOND, queue: .main) { [weak self] time in
            guard let strongSelf = self else {return}
            strongSelf.update(timeMs: Int64(CMTimeGetSeconds(time)) * MS_IN_SECOND_64)
        }
    }

    private func removePeriodicTimeObserver() {
        if let timeObserverToken = timeObserverToken {
            avPlayer.removeTimeObserver(timeObserverToken)
            self.timeObserverToken = nil
        }
    }
}
