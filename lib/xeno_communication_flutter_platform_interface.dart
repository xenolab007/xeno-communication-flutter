import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'xeno_communication_flutter_method_channel.dart';

abstract class XenoCommunicationFlutterPlatform extends PlatformInterface {
  /// Constructs a XenoCommunicationFlutterPlatform.
  XenoCommunicationFlutterPlatform() : super(token: _token);

  static final Object _token = Object();

  static XenoCommunicationFlutterPlatform _instance =
      MethodChannelXenoCommunicationFlutter();

  /// The default instance of [XenoCommunicationFlutterPlatform] to use.
  ///
  /// Defaults to [MethodChannelXenoCommunicationFlutter].
  static XenoCommunicationFlutterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [XenoCommunicationFlutterPlatform] when
  /// they register themselves.
  static set instance(XenoCommunicationFlutterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool> sdkInit(String apiKey) {
    throw UnimplementedError('sdkInit() has not been implemented.');
  }

  Future<bool> setUser({
    String? phone,
    String? email,
    String? countryCode,
    String? name,
  }) {
    throw UnimplementedError('setUser() has not been implemented.');
  }

  Future<bool> onTokenReceived(String deviceToken) {
    throw UnimplementedError('onTokenReceived() has not been implemented.');
  }

  Future<bool> onMessageReceived(Map<String, dynamic> message) {
    throw UnimplementedError('onMessageReceived() has not been implemented.');
  }

  Future<bool> unsetUser() {
    throw UnimplementedError('unsetUser() has not been implemented.');
  }
}
