# xeno_communication_flutter

## How to use
- [Repo Url](https://github.com/xenolab007/xeno-communication-flutter.git)
- add this in your pubspec.yml
```agsl
  xeno_communication_flutter:
    git:
      url: https://github.com/xenolab007/xeno-communication-flutter.git
      ref: prod
```

## Add This in application's manifest file
```agsl
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

    <activity android:name="PushPermissionActivity" android:theme="@style/Theme.Transparent" />
```

## Open android permission setting when needed
```agsl
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    startActivity(settingsIntent)
         }
```