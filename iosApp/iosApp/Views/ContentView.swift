import SwiftUI
import Combine
import Prabhupada
import AVFoundation

struct ContentView: View {
    
    @EnvironmentObject var theme: Theme
    
    @State
    private var componentHolder: ComponentHolder<RootComponentImpl>
    
    
    init(_ deps: RootDeps) {
        _componentHolder = State(
            initialValue: ComponentHolder<RootComponentImpl> {
                RootComponentImpl(
                    componentContext: $0,
                    storeFactory: DefaultStoreFactory(),
                    deps: deps
                )
            }
        )
    }
    
    var body: some View {
        RootView(componentHolder.component)
            .onAppear {
                //deps.connectivityObserver.start()
                LifecycleRegistryExtKt.resume(self.componentHolder.lifecycle)
                
                UIApplication.shared.beginReceivingRemoteControlEvents()
            }
            .onDisappear {
                //deps.connectivityObserver.stop()
                LifecycleRegistryExtKt.stop(self.componentHolder.lifecycle)
                
                UIApplication.shared.endReceivingRemoteControlEvents()
            }
            .environmentObject(theme)
    }
    
    
}

//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView(nil)
//            .environmentObject(themes[0])
//    }
//}
