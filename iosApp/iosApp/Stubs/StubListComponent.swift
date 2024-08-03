//
//  StubListComponent.swift
//  iosApp
//
//  Created by Aleksey Blekot on 11.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada

class StubListComponent: ListComponent {
    func youtube() {}
    func resolveDescription(id: ShlokaId) -> String {
        return "dharmaḥ svanuṣṭhitaḥ puṁsāṁ"
    }
    func add() {}
    func save() {}
    func remove(id: ShlokaId) {}
    func moveUp(id: ShlokaId) {}
    func moveDown(id: ShlokaId) {}
    func select(id: ShlokaId, isSelected: Bool) {}
    func details(config: ShlokaConfig) {}
    func play() {}
    func play(config: ShlokaConfig) {}
    func settings() {}
    func donations() {}
    func shareApp() {}
    func saveShloka(config: ShlokaConfig) {}
    func onTutorialCompleted() {}
    func onTutorialSkipped() {}
    func setList(type: ListId) {}

    func onPreRatingAccepted() {}
    func onPreRatingClosed() {}

    let flow: Value<ListState> =
        valueOf(
            ListState(
                config: mockListConfig(),
                availableLists: [
                    ListPresentation(type: .bg, title: "BG", isSelected: true)
                ],
                locale: "en",
                hasChanges: false,
                shouldShowTutorial: false,
                shouldShowPreRating: false
            )
        )
}
