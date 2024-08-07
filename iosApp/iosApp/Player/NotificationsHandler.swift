//
//  NotificationsHandler.swift
//  iosApp
//
//  Created by Aleksey Blekot on 21.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import AVFoundation

class NotificationsHandler: NSObject {
    
    private let player: Player
    private let audioSession = AVAudioSession.sharedInstance()
    
    init(player: Player) {
        self.player = player
        super.init()
        
        setupNotifications()
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    private func setupNotifications() {
        //        NotificationCenter.default.addObserver(
        //            self,
        //            selector: #selector(songEnded),
        //            name: .AVPlayerItemDidPlayToEndTime,
        //            object: player.currentItem
        //        )
        
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleRouteChange(_:)),
            name: AVAudioSession.routeChangeNotification,
            object: audioSession
        )
        
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleInterruption(_:)),
            name: AVAudioSession.interruptionNotification,
            object: nil
        )
        
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleAudioServiceReset(_:)),
            name: AVAudioSession.mediaServicesWereResetNotification,
            object: audioSession
        )
        
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleSecondaryAudio(_:)),
            name: AVAudioSession.silenceSecondaryAudioHintNotification,
            object: audioSession
        )
    }
    
    @objc class func songEnded(_ notification: Notification)
    {
        print("song ended: \(notification)")
    }
    
    @objc func handleRouteChange(_ notification: Notification) {
        print("handleRouteChange: \(notification)")
        
        guard let userInfo = notification.userInfo,
              let reasonValue = userInfo[AVAudioSessionRouteChangeReasonKey] as? UInt,
              let reason = AVAudioSession.RouteChangeReason(rawValue: reasonValue) else { return }
        
        var headphonesConnected: Bool = false
        
        switch reason {
        case .newDeviceAvailable:
            for output in AVAudioSession.sharedInstance().currentRoute.outputs where output.portType == AVAudioSession.Port.headphones {
                headphonesConnected = true
            }
        case .oldDeviceUnavailable:
            // Handle old device removed.
            // Pause
            if let previousRoute =
                userInfo[AVAudioSessionRouteChangePreviousRouteKey] as? AVAudioSessionRouteDescription {
                for output in previousRoute.outputs where output.portType == AVAudioSession.Port.headphones {
                    headphonesConnected = false
                }
            }
            
        default: ()
        }
        
        debugPrint("headphonesConnected == \(headphonesConnected)")
    }
    
    @objc func handleInterruption(_ notification: Notification) {
        guard let userInfo = notification.userInfo,
              let typeValue = userInfo[AVAudioSessionInterruptionTypeKey] as? UInt,
              let type = AVAudioSession.InterruptionType(rawValue: typeValue) else {return}
        
        switch type {
        case .began:
            // Interruption began, take appropriate actions (save state, update user interface)
            print("Audio session interruption began")
            self.player.pause()
        case .ended:
            guard let interruptionOptionValue = userInfo[AVAudioSessionInterruptionOptionKey] as? UInt else { return }
            let interruptionOptions = AVAudioSession.InterruptionOptions(rawValue: interruptionOptionValue)
            
            print("Audio session interruption ended")
            if interruptionOptions.contains(.shouldResume) {
                // Interruption Ended - playback should resume
                self.player.play()
            }
        @unknown default:
            break
        }
        
    }
    
    @objc func handleAudioServiceReset(_ notification: Notification) {
        print("AudioServiceReset: \(notification)")
        //        * Dispose of orphaned audio objects (such as players, recorders, converters, or audio queues) and create new ones
        //        * Reset any internal audio states being tracked, including all properties of AVAudioSession
        //        * When appropriate, reactivate the AVAudioSession instance using the setActive:error: method
    }
    
    @objc func handleSecondaryAudio(_ notification: Notification) {
        // Determine hint type
        guard let userInfo = notification.userInfo,
              let typeValue = userInfo[AVAudioSessionSilenceSecondaryAudioHintTypeKey] as? UInt,
              let type = AVAudioSession.SilenceSecondaryAudioHintType(rawValue: typeValue) else {return}
        
        if type == .begin {
            // Other app audio started playing - mute secondary audio
        } else {
            // Other app audio stopped playing - restart secondary audio
        }
        
    }
}
