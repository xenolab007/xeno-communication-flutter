import Flutter
import UIKit

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
      
        let apiKey = args["apiKey"] as! String
        
        print("apiKey : \(String(describing: apiKey))")
        
      result(true)
        
    case "user-authenticate":
        
        let countryCode = args["phone"] as? String?
        let phone = args["phone"] as? String?
        let email = args["email"] as? String?
        let name = args["name"] as? String?
        
        print("countryCode : \(String(describing: countryCode))")
        print("phone : \(String(describing: phone))")
        print("email : \(String(describing: email))")
        print("name : \(String(describing: name))")
        
        result(true)
    
    case "update-device-token":
        
        let deviceToken = args["deviceToken"] as? String?
       
        print("deviceToken : \(String(describing: deviceToken))")
        
        result(true)
        
    case "on-message-received":
        
        let payload = args["payload"] as? [String:Any]?
       
        print("payload : \(String(describing: payload))")
        
        result(true)
        
    case "unset-user":
        
        print("unset-user ")
        
        result(true)
       
    default:
      result(FlutterMethodNotImplemented)
    }
  }
}
