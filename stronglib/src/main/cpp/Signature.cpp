//
// Created by LiuZhen on 2019/12/11.
//
#include <jni.h>
#include <string>
#include "utils/SystemUtils.h"
#include "utils/LogUtils.h"

//app包名
static char *package_name = "com.kotlinstrong";

//app签名，在这里配置我们app的正式签名，在so库中，可以保证安全性
static char *app_signature = "308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3139303331333032333131325a170d3439303330353032333131325a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d0030818902818100ae3273df8de6c452ed33bbf409afcc8b98c508e80aa29ab5d471d902a6ac8b08c4290e716f9f9cdf88e378d270c9fc94a1de989b4e4d429e27eb9c7c079b4fe3d2ff5109b8116166c193c41517f9f8c4dc77e3f5f7ad26eeeef89dd77ac375bf89267bedb664dd11fcc42db825b6f8e836a962002d50bf9694b7c1b64e9439250203010001300d06092a864886f70d01010505000381810051a87d1a81f05542a537853843a8ea1c7ec53f2bd891b1f40fb4d74c6f69adebd8c8c82bddbc39b74574095152b4d7fb737c76144f600a6d992039f69b10fab1fd5ed57f7e48fcab839711d22af7117c63d825b4b197eee63a6411a3d5c2d5032fd367f1bdf11e0fa4b5c483c0fd6c1342a4ef76b161c4a3e88ff4c90c7a9b89";

jstring getSignature(JNIEnv *env, jobject obj)
{
    jclass native_class = env->GetObjectClass(obj);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(obj, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jstring pkg_str = getPackname(env, obj);
    Logger("getPackname: %d", pkg_str);
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray)signatures_obj;
//    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString", "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
//    char *c_msg = (char*)env->GetStringUTFChars(str,0);
//    Logger("signsture: %s", c_msg);
    return str;
}
/** 验证程序包和签名 */
jboolean checkSignature(JNIEnv *env, jobject context){

    //根据传入的context对象getPackageName
    jstring pkg_str = getPackname(env, context);
    const char *pkg = env->GetStringUTFChars(pkg_str, NULL);
    //对比
    if (strcmp(package_name, pkg) != 0) {
        Logger("程序包验证失败：%s",pkg);
        return false;
    }
    Logger("程序包验证成功：%s",pkg);
    //调用String的toCharsString
    jstring signature_string = getSignature(env,context);
    //转换为char*
    const char *signature_char = env->GetStringUTFChars(signature_string, NULL);
    Logger("app signature:%s\n", signature_char);
    Logger("cpp signature:%s\n", app_signature);
    //对比签名
    if (strcmp(signature_char, app_signature) == 0) {
        Logger("程序签名验证通过");
        return true;
    } else {
        Logger("程序签名验证失败");
        return false;
    }
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_kotlinstrong_stronglib_cutil_EncryptUtils_checkSignature(
        JNIEnv *env, jobject) {
    return checkSignature(env,getAndroidApplication(env));
}


