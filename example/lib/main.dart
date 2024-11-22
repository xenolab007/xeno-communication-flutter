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
  // final _xenoPlugin = XenoCommunicationFlutter();

  @override
  void initState() {
    super.initState();
    // _xenoPlugin.sdkInit("apiKey");
    // _xenoPlugin.setUser(
    //   phone: "phone",
    //   email: "email",
    //   countryCode: "countryCode",
    //   name: "name",
    // );
    // _xenoPlugin.onTokenReceived("deviceToken");
    // _xenoPlugin.onMessageReceived({"key": "value"});
    // _xenoPlugin.unsetUser();
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
