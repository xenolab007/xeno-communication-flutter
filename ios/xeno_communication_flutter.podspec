Pod::Spec.new do |s|
  s.name             = 'xeno_communication_flutter'
  s.version          = '0.0.1'
  s.summary          = 'A Flutter plugin for iOS to communicate using XenoCommunicationIos framework.'
  s.description      = <<-DESC
A Flutter plugin that integrates with the XenoCommunicationIos framework for iOS.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files     = 'Classes/**/*'

  # Specify the iOS platform version
  s.platform = :ios, '12.0'

  # Flutter dependency (this should be included as a dependency)
  s.dependency 'Flutter'
#   s.dependency 'XenoCommunicationIos'

  # Exclude the i386 architecture for iOS simulators in Flutter builds
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }

  # Specify the Swift version
  s.swift_version = '5.0'

  s.vendored_frameworks = 'XenoCommunicationIos.framework'


  # Optional: If your plugin needs a privacy manifest or other resources, you can add it like this
  # s.resource_bundles = {'xeno_communication_flutter_privacy' => ['Resources/PrivacyInfo.xcprivacy']}
end
