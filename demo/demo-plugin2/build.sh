
rm build/plugin-debug.zip
./gradlew :plugin2-manager:clean
./gradlew :plugin2-manager:aDebug

./gradlew packageDebugPlugin

adb push build/plugin-debug.zip /data/local/tmp/plugin-debug2.zip
adb push plugin2-manager/build/outputs/apk/debug/plugin2-manager-debug.apk /data/local/tmp/plugin2-manager-debug.apk
