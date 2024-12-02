import 'package:flutter/material.dart';
import 'package:xeno_communication_flutter/xeno_communication_flutter.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _xenoPlugin = XenoCommunicationFlutter();

  @override
  void initState() {
    super.initState();
    init();
  }

  Future<dynamic> init() async {
    await _xenoPlugin.sdkInit("zsdbha795t", isProduction: true);

    await Future.delayed(const Duration(seconds: 10));
    await _xenoPlugin.setUser(
      phone: "9999999999",
      email: "",
      countryCode: "+91",
      name: "",
    );
    await _xenoPlugin.onTokenReceived("deviceToken");
    await _xenoPlugin.requestNotificationPermission();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: const Center(
          child: Text('Running on: '),
        ),
      ),
    );
  }
}
