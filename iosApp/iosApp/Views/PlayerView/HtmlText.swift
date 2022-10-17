//
//  HtmlText.swift
//  iosApp
//
//  Created by Aleksey Blekot on 14.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HtmlText: View {
    let htmlString = """
        <h2>SwiftUI Text with HTML via NSAttributedString</h2>
        <br/>
        <p>This recipe shows how to <strong>format content of a SwiftUI <em>Text</em> with HTML via <em>NSAttributedString</em></strong> - sort of. The solution isn't fully complete as it doesn't support all the HTML tags, nor does it support hyperlinks (even though they're supported in attributed strings) - but it gets the job done most of the time.
        <br/>
        <p>The reason for its limitations is that SwiftUI is surprisingly lacklustre when it comes to working with <em>NSAttributedString</em> in <em>Text</em> views: it has no default support for it, and even trying to include an attributed string directly in a View results in some <font color="blue"><u>weird memory issues</u></font>.<br/>
        <br/>
        First we'll check out the <font color="blue"><u>solution that works</u></font>, which:<br/>
        <ol>
        <li>Supports most HTML formatting options</li>
        <li>has no sizing or layout issues,</li>
        <li>uses a native <em>Text</em>, allowing you to use all <em>Text</em>-specific view modifiers.</li>
        </ol>
        <br/>
        Then, we'll take a look at a <font color="blue"><u>few other attempts</u></font> that fail due to internal SwiftUI issues.
        """
    
    var body: some View {
        Text(html: htmlString, size: 16)
    }
}

struct HtmlText_Previews: PreviewProvider {
    static var previews: some View {
        HtmlText()
    }
}

extension Text {
    init(_ attributedString: NSAttributedString, fColor: Color? = nil) {
        self.init("") // initial, empty Text
        
        // scan the attributed string for distinctly attributed regions
        attributedString.enumerateAttributes(in: NSRange(location: 0, length: attributedString.length),
                                             options: []) { (attrs, range, _) in
            let string = attributedString.attributedSubstring(from: range).string
            var text = Text(string)
            
            // then, read applicable attributes and apply them to the Text
            
            if let font = attrs[.font] as? UIFont {
                // this takes care of the majority of formatting - text size, font family,
                // font weight, if it's italic, etc.
                text = text.font(.init(font))
            }
            
            if let color = fColor {
                text = text.foregroundColor(color)
            } else if let color = attrs[.foregroundColor] as? UIColor {
                text = text.foregroundColor(Color(color))
            }
            
            if let kern = attrs[.kern] as? CGFloat {
                text = text.kerning(kern)
            }
            
            if #available(iOS 14.0, *) {
                if let tracking = attrs[.tracking] as? CGFloat {
                    text = text.tracking(tracking)
                }
            }
            
            if let strikethroughStyle = attrs[.strikethroughStyle] as? NSNumber,
               strikethroughStyle != 0 {
                if let strikethroughColor = (attrs[.strikethroughColor] as? UIColor) {
                    text = text.strikethrough(true, color: Color(strikethroughColor))
                } else {
                    text = text.strikethrough(true)
                }
            }
            
            if let underlineStyle = attrs[.underlineStyle] as? NSNumber,
               underlineStyle != 0 {
                if let underlineColor = (attrs[.underlineColor] as? UIColor) {
                    text = text.underline(true, color: Color(underlineColor))
                } else {
                    text = text.underline(true)
                }
            }
            
            if let baselineOffset = attrs[.baselineOffset] as? NSNumber {
                text = text.baselineOffset(CGFloat(baselineOffset.floatValue))
            }
            
            // append the newly styled subtext to the rest of the text
            self = self + text
        }
    }
}

extension Text {
    init(html htmlString: String, // the HTML-formatted string
         raw: Bool = false, // set to true if you don't want to embed in the doc skeleton
         size: CGFloat? = nil, // optional document-wide text size
         color: Color? = nil,
         fontFamily: String = "-apple-system") { // optional document-wide font family
        let fullHTML: String
        if raw {
            fullHTML = htmlString
        } else {
            var sizeCss = ""
            if let size = size {
                sizeCss = "font-size: \(size)px;"
            }
            
            fullHTML = """
        <!doctype html>
         <html>
            <head>
              <style>
                body {
                  font-family: \(fontFamily);
                  \(sizeCss)
                }
              </style>
            </head>
            <body>
              \(htmlString.replacingOccurrences(of: "</br><br>", with: "<br>"))
            </body>
          </html>
        """
        }
        
        let attributedString: NSAttributedString
        if let data = fullHTML.data(using: .unicode),
           let attrString = try? NSAttributedString(data: data,
                                                    options: [.documentType: NSAttributedString.DocumentType.html],
                                                    documentAttributes: nil) {
            attributedString = attrString
        } else {
            attributedString = NSAttributedString()
        }
        
        self.init(attributedString, fColor: color) // uses the NSAttributedString initializer
    }
}
