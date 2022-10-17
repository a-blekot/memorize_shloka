//
//  MailView.swift
//  iosApp
//
//  Created by Aleksey Blekot on 16.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MessageUI
import Prabhupada

struct MailView: UIViewControllerRepresentable {

    @Binding var isShowing: Bool
    @Binding var result: Result<MFMailComposeResult, Error>?

    class Coordinator: NSObject, MFMailComposeViewControllerDelegate {

        @Binding var isShowing: Bool
        @Binding var result: Result<MFMailComposeResult, Error>?

        init(isShowing: Binding<Bool>,
             result: Binding<Result<MFMailComposeResult, Error>?>) {
            _isShowing = isShowing
            _result = result
        }

        func mailComposeController(_ controller: MFMailComposeViewController,
                                   didFinishWith result: MFMailComposeResult,
                                   error: Error?) {
            defer {
                isShowing = false
            }
            guard error == nil else {
                self.result = .failure(error!)
                return
            }
            self.result = .success(result)
        }
    }

    func makeCoordinator() -> Coordinator {
        return Coordinator(isShowing: $isShowing,
                           result: $result)
    }

    func makeUIViewController(context: UIViewControllerRepresentableContext<MailView>) -> MFMailComposeViewController {
        let vc = MFMailComposeViewController()
        vc.mailComposeDelegate = context.coordinator
        
        let title = MR.strings().email_title.resolve()
        let body = MR.strings().email_body.resolve()
        vc.setToRecipients(["aleksey.blekot@gmail.com"])
        vc.setSubject(title)
        vc.setMessageBody(body, isHTML: false)
        
        return vc
    }

    func updateUIViewController(_ uiViewController: MFMailComposeViewController,
                                context: UIViewControllerRepresentableContext<MailView>) {

    }
}

//struct MailView_Previews: PreviewProvider {
//    static var previews: some View {
//        MailView()
//    }
//}
