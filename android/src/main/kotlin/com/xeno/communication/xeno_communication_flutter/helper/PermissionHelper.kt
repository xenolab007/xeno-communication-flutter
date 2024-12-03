package com.xeno.communication.xeno_communication_flutter.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper {
    var callback: PermissionCallback? = null
    val notificationPermissionCode = 10000

    fun askNotificationPermission(
        activity: Activity,
        callback: PermissionCallback
    ) {
        this.callback = callback

        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (hasPermission(activity, Manifest.permission.POST_NOTIFICATIONS)) {
                callback.onPermissionResult(true)
            } else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.

                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    notificationPermissionCode
                )
            } else {
                // Directly ask for the permission
                //requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    notificationPermissionCode
                )
            }
        } else {
            // Below Android 13 You don't need to ask for notification permission.
            callback.onPermissionResult(true)
        }
    }

    private fun hasPermission(activity: Activity, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun handlePermissionResult(requestCode: Int, resultCode: Int) {
        if (requestCode == 1) {
            if (resultCode == PackageManager.PERMISSION_GRANTED) {
                Log.i("PermissionHelper", "Permission granted")
                this.callback?.onPermissionResult(true)
            } else {
                this.callback?.onPermissionResult(false)
                Log.i("PermissionHelper", "Permission denied")
            }
        }
    }
}