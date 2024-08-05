//
//  Player.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.05.2022.
//  Copyright © 2022 orgName. All rights reserved.


import Foundation
import AVFoundation
import MediaPlayer
import Prabhupada

class Player: NSObject, ObservableObject {
    private let audioSession = AVAudioSession.sharedInstance()
    private let player = AVPlayer()
    private let tts = AVSpeechSynthesizer()
    
    private var remoteControlHandler: RemoteControlHandler? = nil
    private var notificationsHandler: NotificationsHandler? = nil
    
    private let playerBus: PlayerBus
    
    private var currentTask: Task = StopTask()
    private var isSessionSetup = false
    
    var status: PlayerStatus = .none {
        didSet {
            onStatusChanged(status)
        }
    }
    
    init (_ playerBus: PlayerBus) {
        self.playerBus = playerBus
        super.init()
        initPlayer()
        initHandlers()
        observePlayerBus()
    }
    
    deinit {
        UIApplication.shared.endReceivingRemoteControlEvents()
        self.clean()
        //periodicTimeObserver = nil
        notificationsHandler = nil
        remoteControlHandler = nil
    }
    
    private func clean() {
        player.pause()
    }
    
    private func initPlayer() {
        addStatusObserver()
    }
    
    func initHandlers() {
        remoteControlHandler = RemoteControlHandler(player: self)
        notificationsHandler = NotificationsHandler(player: self)
    }
    
    func observePlayerBus() {
        playerBus.observeTasks(onEach: observeTasks, scope: nil)
    }
    
    private func addStatusObserver() {
        debugPrint("IOs addStatusObserver -> ")
        let keysToObserve = ["currentItem","rate"]
        for key in keysToObserve {
            player.addObserver(self, forKeyPath: key, options: [.new, .old, .initial], context: nil)
        }
    }
    
    public override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        debugPrint(String(describing: keyPath))
        
        guard let currentItem = self.currentItem else {
            self.status = .none
            return
        }
        
        switch keyPath {
        case "currentItem":
            self.status = .loading
            currentItem.addObserver(self, forKeyPath: "status", options: .new, context: nil)
            self.updateNowPlaying()
            break
            
        case "status":
            if let item = object as? AVPlayerItem {
                switch (item.status) {
                case .unknown:
                    self.status = .none
                case .readyToPlay:
                    if player.rate > 0 {
                        self.status = .playing
                    } else {
                        self.status = .readyToPlay
                    }
                case .failed:
                    self.status = .failed
                @unknown default:
                    // Handle any future cases
                    self.status = .none
                }
            }
            break
        case "rate":
            self.status = player.rate > 0 ? .playing : .paused
            break
            
        default:
            debugPrint("KeyPath: \(String(describing: keyPath)) not handeled in observer")
        }
        
    }
    
    private func onStatusChanged(_ status: PlayerStatus) {
        NapierProxyKt.d(message: "onStatusChanged \(status)", tag: "IOS_PLAYER")
        switch status {
        case .none:
            break
        case .loading:
            break
        case .failed:
            break
        case .readyToPlay:
            NapierProxyKt.d(message: "onPlaybackReady", tag: "IOS_PLAYER")
            if (currentTask is SetTrackTask) {
                NapierProxyKt.d(message: "PlayerFeedback.Ready", tag: "IOS_PLAYER")
                playerBus.update(feedback: PlayerFeedbackReady())
            }
        case .playing:
            NapierProxyKt.d(message: "onPlaybackStarted", tag: "IOS_PLAYER")
            
            if (currentTask is SetTrackTask) {
                NapierProxyKt.d(message: "PlayerFeedback.Ready", tag: "IOS_PLAYER")
                playerBus.update(feedback: PlayerFeedbackReady())
                break
            }
            
            guard let currentTask = currentTask as? PlayTask else { break }
            NapierProxyKt.d(message: "PlayerFeedback.Started (duration = \(currentTask.duration))", tag: "IOS_PLAYER")
            playerBus.update(feedback: PlayerFeedbackStarted(durationMs: currentTask.duration))
            
        case .paused:
            break
        }
    }
    
    private func setupAudioSession() {
        do {
            try audioSession.setCategory(.playback, mode: .default, options: [.allowAirPlay, .defaultToSpeaker])
            let _ = try audioSession.setActive(true)
            UIApplication.shared.beginReceivingRemoteControlEvents()
        } catch let error as NSError {
            debugPrint("Activate AVAudioSession failed.\n \(error)")
        }
    }
    
    private func observeTasks(_ task: Task) {
        debugPrint("IOs observeTasks -> \(task)")
        currentTask = task
        switch(task){
        case is SetTrackTask: setTrack((task as! SetTrackTask))
        case is PlayTask: play((task as! PlayTask))
        case is PlayTranslationTask: playTranslation((task as! PlayTranslationTask))
        case is PauseTask, is IdleTask, is NoAudioTask: pause()
        case is StopTask: pause()
        default: debugPrint("default")
        }
    }
    
    func pause() {
        debugPrint("IOs Player Pause")
        player.pause()
        pauseTts()
    }
    
    private func pauseTts() {
        tts.delegate = nil
        tts.stopSpeaking(at: .immediate)
    }
    
    func play() {
        debugPrint("IOs Player Play no args")
        
        if !isSessionSetup {
            setupAudioSession()
        }
        
        player.play()
    }
    
    func play(_ task: PlayTask) {
        debugPrint("IOs Player Play")
        
        if !isSessionSetup {
            setupAudioSession()
        }
        
        player.pause()
        seekTo(timeMs: task.startMs)
        player.play()
    }
    
    func playTranslation(_ task: PlayTranslationTask) {
        if (ttsIsNotAvailable(locale: SettingsKt.locale)) {
            onTtsComplete(id: task.id.name)
            return
        }
        
        pause()
        
        let utterance = TtsUtterance(id: task.id.name, text: task.text)
        utterance.voice = getVoice(for: SettingsKt.locale)
        utterance.rate = AVSpeechUtteranceDefaultSpeechRate * PlayTranslationTaskKt.SPEECH_RATE * SettingsKt.audioSpeed
        
        tts.delegate = self
        tts.speak(utterance)
    }
    
    private func getVoice(for locale: String) -> AVSpeechSynthesisVoice? {
        switch locale {
        case "en": return AVSpeechSynthesisVoice(language: "en-US")
        case "uk": return AVSpeechSynthesisVoice(language: "uk-UK")
        default: return AVSpeechSynthesisVoice(language: "ru-RU")
        }
    }
    
    private func ttsIsNotAvailable(locale: String) -> Bool {
        return AVSpeechSynthesisVoice
            .speechVoices()
            .first(where: { $0.language.contains(locale) }) == nil
    }
    
    private func onTtsComplete(id: String) {
        NapierProxyKt.d(message: "onTtsComplete", tag: "IOS_PLAYER")
        guard let currentTask = currentTask as? PlayTranslationTask else {return}
        let currentId = currentTask.id
        
        if (currentId.name == id) {
            NapierProxyKt.d(message: "PlayerFeedback.Ready", tag: "IOS_PLAYER")
            playerBus.update(feedback: PlayerFeedbackReady())
        }
    }
    
    func setTrack(_ task: SetTrackTask) {
        pause()
        if (task.hasAudio) {
            let playerItem = playerItem(task)
            player.replaceCurrentItem(with: playerItem)
            player.rate = SettingsKt.audioSpeed
        }
    }
    
    private func seekTo(timeMs: Int64) {
        let time = CMTime(value: timeMs, timescale: MS_IN_SECOND)
        player.seek(to: time, toleranceBefore: CMTime.zero, toleranceAfter: CMTime.zero)
    }
    
    func updateNowPlaying() {
        // Define Now Playing Info
        guard let currentItem = currentItem else { return }
        
        var nowPlayingInfo = [String : Any]()
        nowPlayingInfo[MPMediaItemPropertyTitle] = currentItem.itemInfo.title
        if let image = UIImage(named: "lockscreen") {
            nowPlayingInfo[MPMediaItemPropertyArtwork] =
            MPMediaItemArtwork(boundsSize: image.size) { size in
                return image
            }
        }
        nowPlayingInfo[MPMediaItemPropertyAlbumTitle] = "Memorize Shloka"
        nowPlayingInfo[MPNowPlayingInfoPropertyElapsedPlaybackTime] = player.currentItem?.currentTime().seconds
        nowPlayingInfo[MPMediaItemPropertyPlaybackDuration] = player.currentItem?.asset.duration.seconds
        nowPlayingInfo[MPNowPlayingInfoPropertyPlaybackRate] = player.rate
        // Set the metadata
        MPNowPlayingInfoCenter.default().nowPlayingInfo = nowPlayingInfo
    }
}

extension Player {
    private func playerItem(_ task: SetTrackTask) -> PlayerItem {
        PlayerItem(
            asset: asset(task),
            index: Int(task.index),
            itemInfo: itemInfo(task)
        )
    }
    
    private func asset(_ task: SetTrackTask) -> AVAsset {
        let asset = ConfigReaderKt.getAsset(id: task.id)
        return AVAsset(url: asset.url)
    }
    
    private func itemInfo(_ task: SetTrackTask) -> PlayerItemInfo {
        PlayerItemInfo (
            id: task.id.name,
            title: task.title,
            description: task.description,
            duration: task.duration
        )
    }
}

extension Player {
    var isPlaying: Bool {
        player.rate > 0.0
    }
    
    var currentItem: PlayerItem? {
        player.currentItem as? PlayerItem
    }
}

class TtsUtterance : AVSpeechUtterance {
    private var id: String
    override var description: String {
        get {
            return id
        }
        set(newValue) {
            id = newValue
        }
    }
    
    init(id: String, text: String) {
        self.id = id
        super.init(string: text)
    }
    
    required init?(coder: NSCoder) {
        self.id = ""
        super.init(coder: coder)
    }
}

extension Player: AVSpeechSynthesizerDelegate {
    
    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didFinish utterance: AVSpeechUtterance) {
        onTtsComplete(id: utterance.description)
    }
    
    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didCancel utterance: AVSpeechUtterance) {
        onTtsComplete(id: utterance.description)
    }
}
