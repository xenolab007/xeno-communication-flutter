import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'xeno_communication_flutter_platform_interface.dart';

/// An implementation of [XenoCommunicationFlutterPlatform] that uses method channels.
class MethodChannelXenoCommunicationFlutter
    extends XenoCommunicationFlutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('xeno/xeno_communication_flutter');

  @override
  Future<bool> sdkInit(
    String apiKey, {
    bool isProduction = false,
  }) async {
    Map<String, dynamic> arguments = {
      "apiKey": apiKey,
      "isProduction": isProduction
    };
    await methodChannel.invokeMethod<bool>('sdk-initialize', arguments);
    return true;
  }

  @override
  Future<bool> setUser({
    String? phone,
    String? email,
    String? countryCode,
    String? name,
  }) async {
    Map<String, String?> arguments = {
      "phone": phone,
      "email": email,
      "countryCode": countryCode,
      "name": name,
    };
    await methodChannel.invokeMethod<bool>('user-authenticate', arguments);
    return true;
  }

  @override
  Future<bool> onTokenReceived(String deviceToken) async {
    Map<String, String> arguments = {
      "deviceToken": deviceToken,
    };
    await methodChannel.invokeMethod<bool>('update-device-token', arguments);
    return true;
  }

  @override
  Future<bool> onMessageReceived(Map<String, dynamic> message) async {
    Map<String, dynamic> arguments = {
      "payload": jsonEncode(message),
    };
    await methodChannel.invokeMethod<bool>(
      'on-message-received',
      arguments,
    );
    return true;
  }

  @override
  Future<bool> unsetUser() async {
    Map<String, dynamic> arguments = {};
    await methodChannel.invokeMethod<bool>('unset-user', arguments);
    return true;
  }
}
