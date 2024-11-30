package com.xeno.communication.xeno_communication_flutter

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.xeno.communication.xeno_communication_flutter.helper.PermissionHelper

class PushPermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                    if (isGranted) {
                        sendBroadcast(Intent(PermissionHelper.ACTION_PERMISSIONS_GRANTED))
                    } else {
                        sendBroadcast(Intent(PermissionHelper.ACTION_PERMISSIONS_DENIED))
                    }
                    finish()
                }

            return requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            sendBroadcast(Intent(PermissionHelper.ACTION_PERMISSIONS_GRANTED))
            finish()
        }

    }
}