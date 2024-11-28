import 'package:xeno_communication_flutter/xeno_communication_flutter_platform_interface.dart';

class XenoCommunicationFlutter {
  static final XenoCommunicationFlutter _instance =
      XenoCommunicationFlutter._internal();

  XenoCommunicationFlutter._internal();

  factory XenoCommunicationFlutter() {
    return _instance;
  }

  Future<bool> sdkInit(
    String apiKey, {
    bool isProduction = false,
  }) {
    return XenoCommunicationFlutterPlatform.instance.sdkInit(
      apiKey,
      isProduction: isProduction,
    );
  }

  Future<bool> setUser({
    String? phone,
    String? email,
    String? countryCode,
    String? name,
  }) {
    return XenoCommunicationFlutterPlatform.instance.setUser(
      phone: phone,
      email: email,
      countryCode: countryCode,
      name: name,
    );
  }

  Future<bool> onTokenReceived(String deviceToken) {
    return XenoCommunicationFlutterPlatform.instance
        .onTokenReceived(deviceToken);
  }

  Future<bool> onMessageReceived(Map<String, dynamic> message) {
    return XenoCommunicationFlutterPlatform.instance.onMessageReceived(message);
  }

  Future<bool> unsetUser() {
    return XenoCommunicationFlutterPlatform.instance.unsetUser();
  }
}
