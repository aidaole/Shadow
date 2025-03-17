

./gradlew :demo:demo-plugin1:plugin1-manager:clean
./gradlew :demo:demo-plugin1:plugin1-runtime:clean
./gradlew :demo:demo-plugin1:plugin1-loader:clean
./gradlew :demo:demo-plugin1:plugin1-app:clean

./gradlew :demo:demo-plugin1:plugin1-manager:aDebug
./gradlew :demo:demo-plugin1:plugin1-loader:aDebug
./gradlew :demo:demo-plugin1:plugin1-runtime:aDebug

cp "demo/demo-plugin1/plugin1-loader/build/outputs/apk/debug/plugin1-loader-debug.apk" "plugin1-loader/build/outputs/apk/debug/plugin1-loader-debug.apk"

cp "demo/demo-plugin1/plugin1-runtime/build/outputs/apk/debug/plugin1-runtime-debug.apk" "plugin1-runtime/build/outputs/apk/debug/plugin1-runtime-debug.apk"


rm build/plugin-debug.zip

./gradlew :demo:demo-plugin1:plugin1-app:packageDebugPlugin

adb push demo/demo-plugin1/plugin1-manager/build/outputs/apk/debug/plugin1-manager-debug.apk /data/local/tmp
adb push build/plugin-debug.zip /data/local/tmp