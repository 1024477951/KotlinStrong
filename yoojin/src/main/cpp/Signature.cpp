//
// Created by LiuZhen on 2019/12/11.
//
#include <jni.h>
#include <string>
#include "utils/LogUtils.h"

//app包名
static const char *package_name = "com.strong.test";

//app签名，在这里配置我们app的正式签名，在so库中，可以保证安全性
static const char *app_signature = "308203773082025fa00302010202042d0d0709300d06092a864886f70d01010b0500306c310f300d060355040613063030303030303115301306035504080c0ce6b5a6e4b89ce696b0e58cba310f300d06035504070c06e4b88ae6b5b7310f300d060355040a0c06e4b8aae4baba310f300d060355040b0c06e4b8aae4baba310f300d060355040313067374726f6e67301e170d3230313233303038323932315a170d3435313232343038323932315a306c310f300d060355040613063030303030303115301306035504080c0ce6b5a6e4b89ce696b0e58cba310f300d06035504070c06e4b88ae6b5b7310f300d060355040a0c06e4b8aae4baba310f300d060355040b0c06e4b8aae4baba310f300d060355040313067374726f6e6730820122300d06092a864886f70d01010105000382010f003082010a0282010100b3a18d6928c8746e2f46f015325fffd093591bd07b4534bf111c9220042d54bcc6c71d3b2bc6cea8062a107178eaccd65699784f40b4594c2940d976e2e511f798cd02da66523c7bc690d2426214e3bc5066663bc0109825a3a9022a0528523fe7cdc1c3c476bd2eece98dd6b31cbd45b9b7ae502d536e153cb1d319251c1cb5707089f81152f6f2ddbe028a2e568b944cf814d5f4620cdaf1f5bbcb3a537cba465d20645234964b0517d98dbdfecb6afdc60bc728bfd246903d74944928aab132a50198b6932eef095150de19020e4b3bbfe4b870227d1d21bd28423f6333ae01f27bba22996810f82aed159a7d4b8ae39e8c93df9173b8b59272d51d04570f0203010001a321301f301d0603551d0e04160414e8d5c538b7e3ad4a57df1c857dd8ec389df67efe300d06092a864886f70d01010b050003820101007aae016deebf6d950655f19530060a0011599ff847987b4934c95fc8c9c5499db836f2190b589f9ef8de3ab0561f1ecc8941bfe560950d5e5c885c40cb810d5f8b8be3da6eee456cd391e0f869e2ff95d22d3ec7a1aebb5785931e1fde463dd360269bfe8001fe9a172b71580a0c0b4f90051b5326e033f4e82228fe0f51ee31d1c6f998e87ad028c38d37ea5a60c32ccc5e3562e0214893d0385d745f8a66bb1b2946b8a143cb3a3a3daad4bcb0fd1504d07f174d18369a962d7e65368d629c5e0375ce0770a84f6d84a04ad75850a025215006e2ac50444cf307d5c4df3d6cb02b0a5bdc9ec897b68a09b97b9cdf406d872bdf8c889c1265a6d86648050a2c";

/** 防止java层传入恶意的context对象，如果是恶意的context，获取时为null。
 * 应用加了签名验证也能通过工具分析出签名，加载的时候然系统返回你插入进去的签名，传入你自己的context，
 * 从而可以根据自身的context获取到本身的context。
 * 还能增加判断我们工程下某些java类是不是存在，类中方法是不是存在，这样要使用就得把项目中的一些类、
 * 方法全部一模一样的保留才能使用我们的.so库文件，又增加一些复杂度。*/
jobject getAndroidApplication(JNIEnv *env) {
    jclass activityThreadClazz = env->FindClass("android/app/ActivityThread");

    jmethodID jCurrentActivityThread =
            env->GetStaticMethodID(activityThreadClazz,
                                   "currentActivityThread",
                                   "()Landroid/app/ActivityThread;");

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

jstring getSignature(JNIEnv *env, jobject obj)
{
    jclass native_class = env->GetObjectClass(obj);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(obj, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jstring pkg_str = getPackname(env, obj);
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    auto signaturesArray = (jobjectArray)signatures_obj;
//    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString", "()Ljava/lang/String;");
    auto str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
//    char *c_msg = (char*)env->GetStringUTFChars(str,0);
//    Logger("signsture: %s", c_msg);
    return str;
}
/** 验证程序包和签名 */
bool checkSignature(JNIEnv *env, jobject context){

    //根据传入的context对象getPackageName
    jstring pkg_str = getPackname(env, context);
    const char *pkg = env->GetStringUTFChars(pkg_str, nullptr);
    //对比
    if (strcmp(package_name, pkg) != 0) {
        Logger("程序包验证失败：%s",pkg)
        return false;
    }
    Logger("程序包验证成功：%s",pkg);
    //调用String的toCharsString
    jstring signature_string = getSignature(env,context);
    //转换为char*
    const char *signature_char = env->GetStringUTFChars(signature_string, nullptr);
    Logger("app signature:%s\n", signature_char)
    Logger("cpp signature:%s\n", app_signature)
    //对比签名
    if (strcmp(signature_char, app_signature) == 0) {
        Logger("程序签名验证通过")
        return true;
    } else {
        Logger("程序签名验证失败")
        return false;
    }
}

extern "C"
JNIEXPORT bool JNICALL
Java_com_strong_utils_encrypt_EncryptUtils_checkSignature(
        JNIEnv *env, jobject) {
    return checkSignature(env,getAndroidApplication(env));
}


