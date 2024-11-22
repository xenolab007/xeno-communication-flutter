package com.xeno.communication.xeno_communication_flutter

import android.util.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** XenoCommunicationFlutterPlugin */
class XenoCommunicationFlutterPlugin : FlutterPlugin, MethodCallHandler {
    final val TAG = "XenoCommunication"

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "xeno/xeno_communication_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "sdk-initialize") {
            val apiKey = call.argument<String?>("apiKey") as String?
            Log.i(TAG, "API Key: $apiKey")

            result.success(true)
        } else if (call.method == "user-authenticate") {
            val phone = call.argument<String?>("phone") as String?
            val countryCode = call.argument<String?>("countryCode") as String?
            val email = call.argument<String?>("email") as String?
            val name = call.argument<String?>("name") as String?

            Log.i(TAG, "Phone: $phone")
            Log.i(TAG, "Country code: $countryCode")
            Log.i(TAG, "Email: $email")
            Log.i(TAG, "Name: $name")

            result.success(true)
        } else if (call.method == "update-device-token") {
            val token = call.argument<String>("deviceToken") as String
            Log.i(TAG, "Device token: $token")
            result.success(true)
        } else if (call.method == "on-message-received") {
            val msg = call.argument<HashMap<String, Any>>("payload") as HashMap<String, Any>
            Log.i(TAG, "Message received: $msg")
            result.success(true)
        } else if (call.method == "unset-user") {
            Log.i(TAG, "Unset user")
            result.success(true)
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
