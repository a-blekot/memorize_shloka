//
//  Mocks.swift
//  iosApp
//
//  Created by Aleksey Blekot on 11.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Prabhupada

func valueOf<T: AnyObject>(_ value: T) -> Value<T> {
    return MutableValueBuilderKt.MutableValue(initialValue: value) as! MutableValue<T>
}

func mockListConfig(_ id: ListId = ListId.bg) -> ListConfig {
    return ListConfig(
        id: id,
        title: "Bhagavad-gita",
        list: mockShlokaConfigs()
    )
}

func mockShlokaConfigs() -> [ShlokaConfig] {
    return [
        mockShlokaConfig(ShlokaId.bg11),
        mockShlokaConfig(ShlokaId.bg27),
        mockShlokaConfig(ShlokaId.bg213),
        mockShlokaConfig(ShlokaId.bg227),
        mockShlokaConfig(ShlokaId.bg327),
        mockShlokaConfig(ShlokaId.bg42),
        mockShlokaConfig(ShlokaId.bg529),
        mockShlokaConfig(ShlokaId.bg647),
        mockShlokaConfig(ShlokaId.bg714),
        mockShlokaConfig(ShlokaId.bg85),
        mockShlokaConfig(ShlokaId.bg92),
        mockShlokaConfig(ShlokaId.bg914),
        mockShlokaConfig(ShlokaId.bg926),
        mockShlokaConfig(ShlokaId.bg927),
        mockShlokaConfig(ShlokaId.bg108),
        mockShlokaConfig(ShlokaId.bg109),
        mockShlokaConfig(ShlokaId.bg1010),
        mockShlokaConfig(ShlokaId.bg1011),
        mockShlokaConfig(ShlokaId.bg1322),
        mockShlokaConfig(ShlokaId.bg144),
        mockShlokaConfig(ShlokaId.bg1426)
    ]
}

func mockShlokaConfig(_ id: ShlokaId = ShlokaId.sb11813) -> ShlokaConfig {
    return ShlokaConfig(
        shloka: mockShloka(id),
        chunks: mockChunks(),
        isSelected: true
    )
}

func mockShloka(_ id: ShlokaId = ShlokaId.sb11813) -> Shloka {
    return Shloka(id: id, title: "SB 1.18.13", hasAudio: true, extra: false)
}

func mockChunks() -> [Chunk] {
    return [
        mockChunk(),
        mockChunk(),
        mockChunk(),
        mockChunk()
    ]
}

func mockChunk() -> Chunk {
    return Chunk(startMs: 0, endMs: 1000)
}
