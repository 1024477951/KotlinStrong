//
// Created by LiuZhen on 2019/12/19.
//
#include "../../../../../../../../AppData/Local/Android/Sdk/ndk-bundle/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"
#include "../../../../../../../../AppData/Local/Android/Sdk/ndk-bundle/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/c++/v1/string"

#ifndef KOTLINSTRONG_SYSTEMUTILS_H
#define KOTLINSTRONG_SYSTEMUTILS_H

/** 防止java层传入恶意的context对象，如果是恶意的context，获取时为null。
 * 应用加了签名验证也能通过工具分析出签名，加载的时候然系统返回你插入进去的签名，传入你自己的context，
 * 从而可以根据自身的context获取到本身的context。
 * 还能增加判断我们工程下某些java类是不是存在，类中方法是不是存在，这样要使用就得把项目中的一些类、
 * 方法全部一模一样的保留才能使用我们的.so库文件，又增加一些复杂度。*/
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
#endif //KOTLINSTRONG_SYSTEMUTILS_H
