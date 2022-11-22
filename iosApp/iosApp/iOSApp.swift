import SwiftUI
import StoreKit
import FirebaseCore
import FirebaseCrashlytics
import AVFoundation
import Prabhupada

class IosPlatform: Prabhupada.PlatformApi {
    
    let hasTts = true
    let hasInappReview = true
    
    func onEmail() {}
    func onRateUs() {
        guard let urlReview = URL(string: "https://apps.apple.com/app/memorize-shlokas/id6443863948?action=write-review")
            else { return }
        UIApplication.shared.open(urlReview, options: [:], completionHandler: nil)
    }
    func onShareApp() {
        guard let urlShare = URL(string: "https://apps.apple.com/app/memorize-shlokas/id6443863948") else { return }
        let activityVC = UIActivityViewController(activityItems: [urlShare], applicationActivities: nil)
        
        let scenes = UIApplication.shared.connectedScenes
        let windowScenes = scenes.first as? UIWindowScene
        let window = windowScenes?.windows.first
        
        window?.rootViewController?.present(activityVC, animated: true, completion: nil)
        // UIApplication.shared.windows.first?.rootViewController?.present(activityVC, animated: true, completion: nil)
    }
    func onInappReview() {
    #if os(macOS)
        SKStoreReviewController.requestReview()
    #else
        guard let scene = UIApplication.shared.foregroundActiveScene else { return }
        SKStoreReviewController.requestReview(in: scene)
    #endif
        SettingsKt.onInappReviewShown()
    }
    func onSelectTtsVoice() {
        guard let urlReview = URL(string: "App-prefs:root=General&path=ACCESSIBILITY/VOICEOVER/Speech")
            else { return }
        UIApplication.shared.open(urlReview, options: [:], completionHandler: nil)
    }
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    @Environment(\.colorScheme) var colorScheme
    
    private let deps: RootDeps
    private let player: Player
    
    init() {
        let playerBus = PlayerBusImpl(dispatchers: DispatcherProviderImplKt.dispatchers())

        self.player = Player(playerBus)
        self.deps = RootDeps(
            filer: IOsFiler(),
            analytics: AnalyticsIOs(),
            playerBus: playerBus,
            platformApi: IosPlatform(),
            dispatchers: DispatcherProviderImplKt.dispatchers(),
            configReader: IOsConfigReader(),
            billingHelper: nil,
            connectivityObserver: ConnectivityObserverIOS(),
            stringResourceHandler: IOsStringResourceHandler()
        )
        
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView(deps)
                .environmentObject(colorScheme == .dark ? themes[1] : themes[0])
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    
    //    override func remoteControlReceived(with event: UIEvent?) {
    //        musicPlayer.remoteControlReceivedWithEvent(event!)
    //    }
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        application.beginReceivingRemoteControlEvents()
        FirebaseApp.configure()
        LocaleKt.checkLocale(systemLocale: NSLocale.current.languageCode ?? "ru")
        SettingsKt.onAppLaunch()
        
        if AppUtil.isInDebugMode {
            NapierProxyKt.doInitNapier(antilog: DebugAntilog.init(defaultTag: "IOS_DEBUG"))
        } else {
            NapierProxyKt.doInitNapier(antilog: CrashlyticsAntilog())
        }
        return true
    }
    
    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
}

extension UIApplication {
    var foregroundActiveScene: UIWindowScene? {
        connectedScenes
            .first(where: { $0.activationState == .foregroundActive }) as? UIWindowScene
    }
}
