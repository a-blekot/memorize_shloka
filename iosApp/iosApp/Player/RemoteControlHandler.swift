//
//  RemoteControlHandler.swift
//  iosApp
//
//  Created by Aleksey Blekot on 21.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import MediaPlayer

extension Player {
    func playOrPause() -> PlayerStatus { return .none}
    func skipForward() {}
    func skipBackward() {}
    func seek(toTime: TimeInterval) {}
}

class RemoteControlHandler {
    
    private let commandCenter = MPRemoteCommandCenter.shared()
    
    private let player: Player
    
    init(player: Player) {
        self.player = player
        
        setupRemoteControl()
    }
    
    private func setupRemoteControl() {
        
        setCommandCenterMode(mode: Defaults.commandCenterMode)
        
        commandCenter.togglePlayPauseCommand.addTarget(handler: {[weak self]  (_) -> MPRemoteCommandHandlerStatus in
            debugPrint("togglePlayPauseCommand")
            guard let strongSelf = self else {return .commandFailed}
            
            if strongSelf.player.playOrPause() != .none {
                return .success
            } else {
                return .commandFailed
            }
        })
        commandCenter.playCommand.addTarget { [weak self] event in
            debugPrint("playCommand")
            guard let strongSelf = self else {return .commandFailed}
            
            strongSelf.player.play()
            return checkSatatus(strongSelf)
        }
        commandCenter.pauseCommand.addTarget { [weak self] event in
            debugPrint("pauseCommand")
            guard let strongSelf = self else {return .commandFailed}
            
            strongSelf.player.pause()
            return checkSatatus(strongSelf)
        }
        commandCenter.stopCommand.addTarget(handler: {[weak self] (_) -> MPRemoteCommandHandlerStatus in
            debugPrint("stopCommand")
            guard let strongSelf = self else {return .commandFailed}
            
            strongSelf.player.pause()
            return checkSatatus(strongSelf)
        })
        
        // check status and return MPRemoteCommandHandlerStatus
        func checkSatatus(_ strongSelf: RemoteControlHandler) -> MPRemoteCommandHandlerStatus {
            if strongSelf.player.status != .none {
                return .success
            } else {
                return .commandFailed
            }
        }
        
        // Enters Background
        NotificationCenter.default.addObserver(forName: UIApplication.willResignActiveNotification, object: nil, queue: nil, using: {[weak self] (_) in
            debugPrint("willResignActiveNotification")
            guard let strongSelf = self else {return}
            
            strongSelf.player.updateNowPlaying()
        })
    }
    
    public func setCommandCenterMode(mode: RemoteControlMode) {
        commandCenter.skipBackwardCommand.isEnabled = false // mode == .skip
        commandCenter.skipForwardCommand.isEnabled = false // mode == .skip
        
        commandCenter.nextTrackCommand.isEnabled = false // mode == .nextprev
        commandCenter.previousTrackCommand.isEnabled = false // mode == .nextprev
        
        commandCenter.seekForwardCommand.isEnabled = false
        commandCenter.seekBackwardCommand.isEnabled = false
        commandCenter.changePlaybackPositionCommand.isEnabled = false
        commandCenter.changePlaybackRateCommand.isEnabled = false
        
        commandCenter.playCommand.isEnabled = true
        commandCenter.pauseCommand.isEnabled = true
        commandCenter.togglePlayPauseCommand.isEnabled = true
        commandCenter.stopCommand.isEnabled = true
    }
}
