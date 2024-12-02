import Flutter
import UIKit
import XenoCommunicationIos

public class XenoCommunicationFlutterPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "xeno/xeno_communication_flutter", binaryMessenger: registrar.messenger())
    let instance = XenoCommunicationFlutterPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
      
      let args = call.arguments as! [String:Any]
      
    switch call.method {
    case "sdk-initialize":
      
        let apiKey = args["apiKey"] as? String?
        let isProduction = args["isProduction"] as! Bool
        
        // print("apiKey : \(String(describing: apiKey))")
        
        if (apiKey != nil) {
            XenoSDK.initialise(config: XenoConfig(apiKey: apiKey!!, isProduction: isProduction))
        }
                       
      result(true)
        
    case "user-authenticate":
        
        let countryCode = args["countryCode"] as? String?
        let phone = args["phone"] as? String?
        let email = args["email"] as? String?
        let name = args["name"] as? String?
        
            XenoSDK.instance?.setUser(country: countryCode ?? "",
                                      phone: phone ?? "",
                                      email: email ?? "",
                                      name: name ?? "") { resultData in
               
                result(true)
            }
        
    
    case "update-device-token":
        
        let deviceToken = args["deviceToken"] as? String?
       
        print("deviceToken : \(String(describing: deviceToken))")
        
        if ( deviceToken != nil ){
            XenoSDK.instance?.updateDeviceToken(token: deviceToken!!) { resultData in
               
                result(true)
                return
            }
        }
        
        result(false)
        
        
    case "request-notification-permission":
        
//        let deviceToken = args["deviceToken"] as? String?
        
        let options: UNAuthorizationOptions = [.alert, .sound, .badge]
        UNUserNotificationCenter.current().requestAuthorization(options: options) { (granted, error) in
            if granted {
                XenoSDK.instance?.setNotificationPermission(hasPermission: true) { resultData in
                    
                    result(true)
                }
                print("Permission for push notifications allowed!")
            } else {
                XenoSDK.instance?.setNotificationPermission(hasPermission: false) {
                    resultData in
                    
                    result(false)
                    print("Permission for push notifications denied.")
                }
            }
        }
        
    case "on-message-received":
        
        if let jsonString = args["payload"] as? String, let jsonData = jsonString.data(using: .utf8) {
            do {
                // Deserialize into [String: [String: Any]] type
                if let jsonObj = try JSONSerialization.jsonObject(with: jsonData, options: []) as? [String: [String: Any]] {
                    print(jsonObj)
                    XenoSDK.instance?.onMessageReceived(remoteMessage: jsonObj) { resultData in
                       
                        result(true)
                    }
                } else {
                    print("Failed to parse JSON into [String: [String: Any]]")
                    result(false)
                }
            } catch {
                print("Failed to convert JSON string to object: \(error.localizedDescription)")
                result(false)
            }
        }
            
    case "unset-user":
        
        print("unset-user ")
        
            
        XenoSDK.instance?.unsetUser { resultData in
           
            result(true)
        }

       
    default:
      result(FlutterMethodNotImplemented)
    }
  }
}
