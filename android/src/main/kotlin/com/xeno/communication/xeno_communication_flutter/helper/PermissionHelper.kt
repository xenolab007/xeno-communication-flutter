package com.xeno.communication.xeno_communication_flutter.helper

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import com.xeno.communication.xeno_communication_flutter.PushPermissionActivity


object PermissionHelper {
    const val ACTION_PERMISSIONS_GRANTED = "ACTION_PERMISSIONS_GRANTED"
    const val ACTION_PERMISSIONS_DENIED = "ACTION_PERMISSIONS_DENIED"

    fun requestPermission(activity: Activity, callback: PermissionCallback) {
        // Define the BroadcastReceiver
        val permissionReceiver = object : BroadcastReceiver() {
            init {
                val intentFilter = IntentFilter().apply {
                    addAction(ACTION_PERMISSIONS_GRANTED)
                    addAction(ACTION_PERMISSIONS_DENIED)
                }

                // Register the receiver based on the Android version
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    activity.registerReceiver(this, intentFilter, Context.RECEIVER_NOT_EXPORTED)
                } else {
                    activity.registerReceiver(this, intentFilter)
                }
            }

            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    ACTION_PERMISSIONS_GRANTED -> {
                        callback.onPermissionResult(true)
                        context.unregisterReceiver(this) // Clean up the receiver
                    }

                    ACTION_PERMISSIONS_DENIED -> {
                        callback.onPermissionResult(false)
                        context.unregisterReceiver(this) // Clean up the receiver
                    }

                    else -> {
                        Log.w("requestPermission", "Unexpected action received: ${intent.action}")
                    }
                }
            }
        }

        // Launch the permission activity
        val intent = Intent(activity, PushPermissionActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.startActivity(intent)

    }


//    fun askNotificationPermission(
//        activity: AppCompatActivity,
//        callback: PermissionCallback
//    ) {
//        val requestPermissionLauncher =
//            activity.registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
//                if (isGranted) {
//                    callback.onPermissionGranted()
//                    // Permission is granted. Continue the action or workflow in your app.
//                } else {
//                    callback.onPermissionDenied()
//                    // Explain to the user that the feature is unavailable because the
//                    // feature requires a permission that the user has denied. At the
//                    // same time, respect the user's decision. Don't link to system
//                    // settings in an effort to convince the user to change their
//                    // decision.
//                }
//            }
//
//
//        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (hasPermission(activity, Manifest.permission.POST_NOTIFICATIONS)) {
//                callback.onPermissionGranted()
//            } else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        } else {
//            // Below Android 13 You don't need to ask for notification permission.
//            callback.onPermissionGranted()
//        }
//
//    }
//
//
//    private fun hasPermission(activity: Activity, permission: String): Boolean {
//        return ContextCompat.checkSelfPermission(
//            activity,
//            permission
//        ) == PackageManager.PERMISSION_GRANTED
//    }

}