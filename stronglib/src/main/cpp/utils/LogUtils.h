//
// Created by LiuZhen on 2019/12/17.
//
#ifndef _LogUtils_H_
#define _LogUtils_H_
#include <jni.h>
#include <android/log.h>

#define Logger(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"LogUtils",FORMAT,##__VA_ARGS__);
#endif
