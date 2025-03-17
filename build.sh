./gradlew :demo:demo-plugin1:plugin1-manager:clean
./gradlew :demo:demo-plugin1:plugin1-runtime:clean
./gradlew :demo:demo-plugin1:plugin1-loader:clean
./gradlew :demo:demo-plugin1:plugin1-app:clean

rm build/plugin-debug.zip

./gradlew :demo:demo-plugin1:plugin1-manager:aDebug
./gradlew :demo:demo-plugin1:plugin1-app:packageDebugPlugin

adb push demo/demo-plugin1/plugin1-manager/build/outputs/apk/debug/plugin1-manager-debug.apk /data/local/tmp
adb push build/plugin-debug.zip /data/local/tmp