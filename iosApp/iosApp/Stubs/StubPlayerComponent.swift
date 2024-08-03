//
//  StubPlayerComponent.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada

class StubPlayerComponent: PlayerComponent {
    func forcePlay() {}
    func forcePause() {}
    func stop() {}
    
    func prev() {}
    func next() {}
    
    let flow: Value<PlayerState> =
        valueOf(
            mockPlayerState()
        )
}

func mockPlayerState() -> PlayerState {
    return PlayerState(
        hasAudio: true,
        title: "ŚB 1.2.16",
        sanskrit: "<i>śuśrūṣoḥ śraddadhānasya<br>vāsudeva-kathā-ruciḥ<br>syān mahat-sevayā viprāḥ<br>puṇya-tīrtha-niṣevaṇāt</i>",
        words: "<i><b>śuśrūṣoḥ</b></i> — one who is engaged in hearing;<br><i><b>śraddadhānasya</b></i> — with care and attention;</br><br><i><b>vāsudeva</b></i> — in respect to Vāsudeva;</br><br><i><b>kathā</b></i> — the message;</br><br><i><b>ruciḥ</b></i> — affinity;</br><br><i><b>syāt</b></i> — is made possible;</br><br><i><b>mahat-sevayā</b></i> — by service rendered to pure devotees;</br><br><i><b>viprāḥ</b></i> — O twice-born;</br><br><i><b>puṇya-tīrtha</b></i> — those who are cleansed of all vice;</br><br><i><b>niṣevaṇāt</b></i> — by service.",
        translation: "O twice-born sages, by serving those devotees who are completely freed from all vice, great service is done. By such service, one gains affinity for hearing the messages of Vāsudeva.",
        durationMs: 20000,
        playbackState: .playing,
        currentRepeat: 21,
        totalRepeats: 33,
        currentShlokaIndex: 11,
        totalShlokasCount: 16,
        totalDurationMs: 600000,
        isAutoplay: true,
        startShloka: nil
    )
}
