//
//  SettingsView.swift
//  iosApp
//
//  Created by Aleksey Blekot on 15.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Combine
import Prabhupada
import MessageUI

extension Binding {
    func onChange(_ handler: @escaping (Value) -> Void) -> Binding<Value> {
        Binding(
            get: { self.wrappedValue },
            set: { newValue in
                self.wrappedValue = newValue
                handler(newValue)
            }
        )
    }
}

struct SettingsView: View {
    @EnvironmentObject var theme: Theme
    
    @ObservedObject
    private var state: ObservableValue<SettingsState>
    
    @State private var mailResult: Result<MFMailComposeResult, Error>? = nil
    @State private var isShowingMailView = false
    @State private var alertNoMail = false
    @State private var infoIsVisible = false
    @State private var repeats: String
    @State private var pause: String
    
    let component: SettingsComponent
    
    init(_ component: SettingsComponent) {
        self.component = component
        self.state = ObservableValue(component.flow)
        _repeats = State(initialValue: String(component.flow.value.repeats))
        _pause = State(initialValue: String(component.flow.value.pause))
    }
    
    var body: some View {
        let state = state.value
        
        ZStack {
            ScrollView {
                VStack(alignment: .leading, spacing: theme.dimens.paddingXS) {
                    Image(systemName: "arrowshape.turn.up.backward.circle")
                        .font(theme.imageFontMedium)
                        .foregroundColor(theme.colors.onSecondaryContainer)
                        .padding(theme.dimens.paddingS)
                        .background(theme.colors.secondaryContainer)
                        .cornerRadius(theme.dimens.radiusM)
                        .onTapGesture {
                            component.back()
                        }
                        .padding(theme.dimens.paddingS)
                        .frame(maxWidth: .infinity)

                    HStack {
                        Image(systemName: "repeat")
                            .font(theme.imageFontSmall)
                        
                        TextField(MR.strings().label_repeats_placeholder.resolve(), text: $repeats)
                            .onSubmit {
                                component.setRepeats(value: Int32(repeats) ?? 1)
                            }
                            .submitLabel(.done)
                            .lineLimit(1)
                            .disableAutocorrection(true)
                            .onReceive(Just(repeats)) { newValue in
                                let filtered = newValue.filter { "0123456789".contains($0) }.prefix(4)
                                if filtered != newValue {
                                    self.repeats = "\(filtered)"
                                }
                            }
                            .frame(height: theme.dimens.buttonHeight)
                    }
                    .overlay(RoundedRectangle(cornerRadius: theme.dimens.radiusS).stroke(lineWidth: theme.dimens.borderS))
                    
                    HStack {
                        Image(systemName: "pause.circle")
                            .font(theme.imageFontSmall)
                        
                        TextField(MR.strings().label_pause_placeholder.resolve(), text: $pause)
                            .onSubmit {
                                component.setPause(value: Int64(pause) ?? 500)
                            }
                            .lineLimit(1)
                            .keyboardType(.numberPad)
                            .textInputAutocapitalization(.never)
                            .disableAutocorrection(true)
                            .onReceive(Just(pause)) { newValue in
                                let filtered = newValue.filter { "0123456789".contains($0) }.prefix(6)
                                if filtered != newValue {
                                    self.pause = "\(filtered)"
                                }
                            }
                            .frame(height: theme.dimens.buttonHeight)
                    }
                    .overlay(RoundedRectangle(cornerRadius: theme.dimens.radiusS).stroke(lineWidth: theme.dimens.borderS))
                    
                    WeekPicker(current: state.week) { week in
                        component.setWeek(value: week.ordinal)
                    }
                    .environmentObject(theme)
                    
                    HStack {
                        StandartCheckBox(state.isAutoplay, theme.colors.primary) { isSelected in
                            component.setAutoplay(value: isSelected)
                        }
                        
                        Text(MR.strings().label_autoplay.resolve())
                            .lineLimit(1)
                            .font(theme.titleLarge)
                    }
                    .padding(.top, theme.dimens.paddingM)
                    
                    HStack(alignment: .top) {
                        StandartCheckBox(state.showClosePlayerDialog, theme.colors.primary) { isSelected in
                            component.setShowClosePlayerDialog(value: isSelected)
                        }
                        
                        Text(MR.strings().label_show_close_player_dialog.resolve())
                            .font(theme.titleLarge)
                    }
                    
                    Divider()
                        .padding(.horizontal, theme.dimens.paddingXS)
                        .frame(height: 20)
                    
                    LocalePicker(current: state.locale) { locale in
                        component.setLocale(value: locale)
                    }
                    .environmentObject(theme)
                    
                    Divider()
                        .padding(.horizontal, theme.dimens.paddingXS)
                        .frame(height: 20)
                    
                    Group {
                        HStack {
                            Image(systemName: "dollarsign.circle.fill")
                                .font(theme.imageFontMedium)
                                .frame(width: 40)
                            
                            Text(MR.strings().label_donate.resolve())
                                .font(theme.titleLarge)
                        }
                        .opacity(0.5)
                        .onTapGesture {
                            //                        component.donations()
                        }
                        
                        HStack {
                            Image(systemName: "envelope.fill")
                                .font(theme.imageFontMedium)
                                .frame(width: 40)
                            
                            Text(MR.strings().label_feedback.resolve())
                                .font(theme.titleLarge)
                        }
                        .onTapGesture {
                            if MFMailComposeViewController.canSendMail() {
                                isShowingMailView.toggle()
                                component.sendEmail()
                            } else {
                                alertNoMail.toggle()
                            }
                        }
                        .opacity(MFMailComposeViewController.canSendMail() ? 1 : 0.5)
                        .sheet(isPresented: $isShowingMailView) {
                            MailView(isShowing: self.$isShowingMailView, result: self.$mailResult)
                        }
                        .alert(isPresented: self.$alertNoMail) {
                            Alert(title: Text("NO MAIL SETUP"))
                        }
                        
                        HStack {
                            Image(systemName: "paperplane.fill")
                                .font(theme.imageFontMedium)
                                .frame(width: 40)
                            
                            Text(MR.strings().label_share_app.resolve())
                                .font(theme.titleLarge)
                        }
                        .onTapGesture {
                            component.shareApp()
                        }

                        HStack {
                            Image(systemName: "star.fill")
                                .font(theme.imageFontMedium)
                                .frame(width: 40)

                            Text(MR.strings().label_rate_us.resolve())
                                .font(theme.titleLarge)
                        }
                        .onTapGesture {
                            component.rateUs()
                        }
                        
                        HStack {
                            Image(systemName: "info.circle.fill")
                                .font(theme.imageFontMedium)
                                .frame(width: 40)
                            
                            Text(MR.strings().label_show_tutorial.resolve())
                                .font(theme.titleLarge)
                        }
                        .opacity(0.5)
                        .onTapGesture {
                            //                        infoIsVisible = true
                            //                        component.onShowTutorial()
                        }
                    }
                }
                .foregroundColor(theme.colors.primary)
                .padding(.horizontal, theme.dimens.horizontalScreenPadding)
            }
            
            //            if (infoIsVisible) {
            //                InfoPopup(
            //                    onSkip = { infoIsVisible.value = false },
            //                    onComplete = {
            //                       infoIsVisible.value = false
            //                       component.onTutorialCompleted()
            //                   }
            //                )
            //            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(theme.colors.background)
    }
}

//struct SettingsView_Previews: PreviewProvider {
//    static var previews: some View {
//        SettingsView()
//    }
//}
