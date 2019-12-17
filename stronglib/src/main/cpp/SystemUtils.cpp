//
// Created by LiuZhen on 2019/12/11.
//
#include <jni.h>
#include <string>
#include <android/log.h>

#define Logger(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"EncryptUtils signature.cpp",FORMAT,##__VA_ARGS__);

jobject getAndroidApplication(JNIEnv *env) {
    jclass activityThreadClazz = env->FindClass("android/app/ActivityThread");

    jmethodID jCurrentActivityThread =
            env->GetStaticMethodID(activityThreadClazz,
                                   "currentActivityThread", "()Landroid/app/ActivityThread;");

    jobject currentActivityThread =
            env->CallStaticObjectMethod(activityThreadClazz, jCurrentActivityThread);

    jmethodID jGetApplication =
            env->GetMethodID(activityThreadClazz, "getApplication", "()Landroid/app/Application;");

    return env->CallObjectMethod(currentActivityThread, jGetApplication);
}

jstring getPackname(JNIEnv *env, jobject obj)
{
    jclass native_class = env->GetObjectClass(obj);
    jmethodID mId = env->GetMethodID(native_class, "getPackageName", "()Ljava/lang/String;");
    jstring packName = (jstring)(env->CallObjectMethod(obj, mId));
//    jstring packName = static_cast<jstring>(env->CallObjectMethod(obj, mId));
    return packName;
}


