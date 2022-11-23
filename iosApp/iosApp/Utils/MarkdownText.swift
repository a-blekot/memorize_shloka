//
//  MarkdownText.swift
//  iosApp
//
//  Created by Aleksey Blekot on 23.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

extension String {
    func toMarkdown() -> LocalizedStringKey {
        let markdownText = self
            .replacingOccurrences(of: "</br><br>", with: "\n")
            .replacingOccurrences(of: "<br>", with: "\n")
            .replacingOccurrences(of: "<i>", with: "*")
            .replacingOccurrences(of: "</i>", with: "*")
            .replacingOccurrences(of: "<b>", with: "**")
            .replacingOccurrences(of: "</b>", with: "**")
        
        return LocalizedStringKey(markdownText)
    }
}
