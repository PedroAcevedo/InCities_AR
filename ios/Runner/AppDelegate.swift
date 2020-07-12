import UIKit
import Flutter

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    
    guard let flutterViewController  = window?.rootViewController as? FlutterViewController else {
        return super.application(application, didFinishLaunchingWithOptions: launchOptions)
    }
    
    let flutterChannel = FlutterMethodChannel.init(name: "com.example.incities_ar",
    binaryMessenger: flutterViewController.binaryMessenger)
    
    flutterChannel.setMethodCallHandler { (flutterMethodCall, flutterResult) in
        
        if flutterMethodCall.method == "showNativeView" {
            UIView.animate(withDuration: 0.5, animations: {
                self.window?.rootViewController = nil
                
                let viewToPush = SecondViewController()
                
                let navigationController = UINavigationController(rootViewController: flutterViewController)
                
                self.window = UIWindow(frame: UIScreen.main.bounds)
                self.window?.makeKeyAndVisible()
                self.window.rootViewController = navigationController
                navigationController.isNavigationBarHidden = false
                navigationController.pushViewController(viewToPush, animated: false)
                
            })
        }
    }
        
    
    GeneratedPluginRegistrant.register(with: self)
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
}
