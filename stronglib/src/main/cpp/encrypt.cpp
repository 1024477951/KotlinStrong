#include <jni.h>//包含一个源代码文件
#include <string>
#include <android/log.h>
#include <cstdio>

#define Logger(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"EncryptUtils encrypt.cpp",FORMAT,##__VA_ARGS__);

/**
 * extern "C" : 实现 C 和 C++ 的混合编程，用于 C++ 代码调用 C 的函数
 * JNIEnv    : 指向全部JNI方法的指针,不能跨线程传递(调用Java的方法 and 获取Java中的变量和对象等等)
 * jobject / jclass : 调用 Java 中 native 方法的实例或 Class 对象
 * */

/** createFile */
extern "C"
JNIEXPORT void JNICALL
Java_com_kotlinstrong_stronglib_cutil_EncryptUtils_createFile(JNIEnv *env, jobject type,
                                                              jstring path_) {
    Logger("createFile path = %s", path_);
    //得到一个UTF-8编码的字符串（java使用 UTF-16 编码的，中文英文都是2字节，jni内部使用UTF-8编码，ascii字符是1字节，中文是3字节）
    const char *normalPath = env->GetStringUTFChars(path_, nullptr);
    if (normalPath == NULL) {
        return;
    }
    //wb:打开或新建一个二进制文件；只允许写数据
    FILE *fp = fopen(normalPath, "wb");

    //把字符串写入到指定的流 stream 中，但不包括空字符。
    fputs("账号：123 \n 密码：123; \n账号：456 \n 密码：456; \n", fp);

    //关闭流 fp。刷新所有的缓冲区
    fclose(fp);
    //释放JVM保存的字符串的内存
    env->ReleaseStringUTFChars(path_, normalPath);//ReleaseStringUTFChars : 表示此内存不在使用，通知JVM回收,用了GetXXX就必须调用ReleaseXXX
}

char password[] = "123";

/** encryption */
extern "C"
JNIEXPORT void JNICALL
Java_com_kotlinstrong_stronglib_cutil_EncryptUtils_encryption(JNIEnv *env, jclass type,
                                                              jstring normalPath_,
                                                              jstring encryptPath_) {
    //获取字符串保存在JVM中内存中
    const char *normalPath = env->GetStringUTFChars(normalPath_, nullptr);
    const char *encryptPath = env->GetStringUTFChars(encryptPath_, nullptr);

    Logger("normalPath = %s, encryptPath = %s", normalPath, encryptPath);

    //rb:只读打开一个二进制文件，允许读数据。
    //wb:只写打开或新建一个二进制文件；只允许写数据
    FILE *normal_fp = fopen(normalPath, "rb");
    FILE *encrypt_fp = fopen(encryptPath, "wb");

    if (normal_fp == nullptr) {
        Logger("%s", "文件打开失败");
        return;
    }

    //一次读取一个字符
    int ch = 0;
    int i = 0;
    size_t pwd_length = strlen(password);//计数器
    while ((ch = fgetc(normal_fp)) != EOF) {//读取文件中的字符
        //写入(异或运算)
        /**  ^(相同为0，不同为1)
             int a=3=011
             int b=6=110
             result : a^b=101=5
          */
        fputc(ch ^ password[i % pwd_length], encrypt_fp);
        i++;
    }

    //关闭流 normal_fp和encrypt_fp。刷新所有的缓冲区
    fclose(normal_fp);
    fclose(encrypt_fp);

    //释放JVM保存的字符串的内存
    env->ReleaseStringUTFChars(normalPath_, normalPath);
    env->ReleaseStringUTFChars(encryptPath_, encryptPath);
}

/** decryption */
extern "C"
JNIEXPORT void JNICALL
Java_com_kotlinstrong_stronglib_cutil_EncryptUtils_decryption(JNIEnv *env, jclass type,
                                                              jstring encryptPath_,
                                                              jstring decryptPath_) {
    ////获取字符串保存在JVM中内存中
    const char *encryptPath = env->GetStringUTFChars(encryptPath_, nullptr);
    const char *decryptPath = env->GetStringUTFChars(decryptPath_, nullptr);

    Logger("encryptPath = %s, decryptPath = %s", encryptPath, decryptPath);

    //rb:只读打开一个二进制文件，允许读数据。
    //wb:只写打开或新建一个二进制文件；只允许写数据
    FILE *encrypt_fp = fopen(encryptPath, "rb");
    FILE *decrypt_fp = fopen(decryptPath, "wb");

    if (encrypt_fp == nullptr) {
        Logger("%s", "加密文件打开失败");
        return;
    }

    int ch;
    int i = 0;
    size_t pwd_length = strlen(password);
    while ((ch = fgetc(encrypt_fp)) != EOF) {
        fputc(ch ^ password[i % pwd_length], decrypt_fp);
        i++;
    }

    //关闭流 encrypt_fp 和 decrypt_fp。刷新所有的缓冲区
    fclose(encrypt_fp);
    fclose(decrypt_fp);

    //释放JVM保存的字符串的内存
    env->ReleaseStringUTFChars(encryptPath_, encryptPath);
    env->ReleaseStringUTFChars(decryptPath_, decryptPath);
}
