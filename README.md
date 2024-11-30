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