//
// Created by LiuZhen on 2019/12/17.
//
#include <jni.h>
#include <android/log.h>

#define Logger(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"LogUtils",FORMAT,##__VA_ARGS__);
