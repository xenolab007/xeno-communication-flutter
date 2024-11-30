package com.xeno.communication.xeno_communication_flutter

import android.app.Activity
import android.content.Context
import android.util.Log
import com.xeno.communication.XenoSDK
import com.xeno.communication.xeno_communication_flutter.helper.PermissionCallback
import com.xeno.communication.xeno_communication_flutter.helper.PermissionHelper
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import org.json.JSONObject

/** XenoCommunicationFlutterPlugin */
class XenoCommunicationFlutterPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    val tag = "XenoCommunicationPlugin"
    val pluginChannelName = "xeno/xeno_communication_flutter"
    private lateinit var context: Context
    private lateinit var activity: Activity

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, pluginChannelName)
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        try {
            if (call.method == "sdk-initialize") {
                val apiKey = call.argument<String?>("apiKey") as String?
                val isProduction = call.argument<Boolean?>("isProduction") as Boolean?
                Log.i(tag, "API Key: $apiKey")
                if (apiKey != null) {
                    XenoSDK.initialise(XenoSDK.XenoConfig(activity, apiKey, isProduction == true))
                }
                result.success(true)

            } else if (call.method == "user-authenticate") {

                val phone = call.argument<String?>("phone") as String?
                val countryCode = call.argument<String?>("countryCode") as String?
                val email = call.argument<String?>("email") as String?
                val name = call.argument<String?>("name") as String?

                Log.i(tag, "Phone: $phone")
                Log.i(tag, "Country code: $countryCode")
                Log.i(tag, "Email: $email")
                Log.i(tag, "Name: $name")

                XenoSDK.instance?.setUser(
                    country = countryCode,
                    phone = phone,
                    email = email,
                    name = name
                )
                result.success(true)

            } else if (call.method == "update-device-token") {

                val token = call.argument<String>("deviceToken") as String
                Log.i(tag, "Device token: $token")

                XenoSDK.instance?.updateDeviceToken(token)
                result.success(true)

            } else if (call.method == "request-notification-permission") {

//                val hasPermission = call.argument<Boolean>("os-permission") as Boolean

                PermissionHelper.requestPermission(activity, object : PermissionCallback {
                    override fun onPermissionResult(hasPermission: Boolean) {
                        XenoSDK.instance?.setNotificationPermission(hasPermission)
                        Log.i(tag, "HasPermission: $hasPermission")
                    }
                })
                result.success(true)

            } else if (call.method == "on-message-received") {

                val msgPayloadJsonStr = call.argument<String>("payload") as String
                Log.i(tag, "Message received: $msgPayloadJsonStr")

                XenoSDK.instance?.onMessageReceived(JSONObject(msgPayloadJsonStr))
                result.success(true)

            } else if (call.method == "unset-user") {

                Log.i(tag, "Unset user")

                XenoSDK.instance?.unsetUser()
                result.success(true)

            } else {
                result.notImplemented()
            }
        } catch (e: Exception) {
            Log.e(tag, "${call.method} => Error: ${e.message}")
            result.error("Error", "${call.method} => ${e.message}", null)
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }
}
