//
// Created by LiuZhen on 2019/12/17.
//
#include "../../../../../../../../AppData/Local/Android/Sdk/ndk-bundle/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"
#include "../../../../../../../../AppData/Local/Android/Sdk/ndk-bundle/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/android/log.h"

#define Logger(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"LogUtils",FORMAT,##__VA_ARGS__);
