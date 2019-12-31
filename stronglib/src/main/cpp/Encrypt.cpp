#include <jni.h>//包含一个源代码文件
#include <string>
#include "utils/LogUtils.h"
#include <cstdio>

/**
 * extern "C" : 实现 C 和 C++ 的混合编程，用于 C++ 代码调用 C 的函数
 * JNIEnv    : 指向全部JNI方法的指针,不能跨线程传递(调用Java的方法 and 获取Java中的变量和对象等等)
 * jobject / jclass : 调用 Java 中 native 方法的实例或 Class 对象
 * 在计算机中 ，所有的文件都是以二进制存储的 ，所有可以进行运算来进行文件的加密解密
 * */

/** createFile */
extern "C"
JNIEXPORT void JNICALL
Java_com_kotlinstrong_stronglib_cutil_EncryptUtils_createFile(JNIEnv *env, jobject type,
                                                              jstring path_) {
    Logger("createFile path = %s", path_);
    //得到一个UTF-8编码的字符串（java使用 UTF-16 编码的，中文英文都是2字节，jni内部使用UTF-8编码，ascii字符是1字节，中文是3字节）
    const char *normalPath = env->GetStringUTFChars(path_, nullptr);
    if (normalPath == nullptr) {
        return;
    }
    //wb:打开或新建一个二进制文件；只允许写数据
    FILE *fp = fopen(normalPath, "wb");

    //把字符串写入到指定的流 stream 中，但不包括空字符。
    fputs("账号：123\n密码：123;\n账号：456\n密码：456;\n", fp);

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
    FILE *normal_fp = fopen(normalPath, "rbe");
    FILE *encrypt_fp = fopen(encryptPath, "wbe");

    if (normal_fp == nullptr) {
        Logger("%s", "文件打开失败");
        return;
    }
    if(encrypt_fp == nullptr) {
        Logger("%s","没有写权限") ;
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
    //获取字符串保存在JVM中内存中
    const char *encryptPath = env->GetStringUTFChars(encryptPath_, nullptr);
    const char *decryptPath = env->GetStringUTFChars(decryptPath_, nullptr);

    Logger("encryptPath = %s, decryptPath = %s", encryptPath, decryptPath);

    //rb:只读打开一个二进制文件，允许读数据。
    //wb:只写打开或新建一个二进制文件；只允许写数据
    FILE *encrypt_fp = fopen(encryptPath, "rbe");
    FILE *decrypt_fp = fopen(decryptPath, "wbe");

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

/*获取文件大小*/
long getFileSize(const char* filePath) {

    FILE* fp = fopen(filePath,"rbe");
    if(fp == nullptr) {
        Logger("%s","FILE is null");
    }
    fseek(fp,0,SEEK_END);
    return ftell(fp);
}

/** 文件分割 */
extern "C"
JNIEXPORT void JNICALL
Java_com_kotlinstrong_stronglib_cutil_EncryptUtils_fileSplit(JNIEnv *env, jclass type,
                                                       jstring splitFilePath_,
                                                       jstring suffix_,
                                                       jint fileNum) {
    const char *splitFilePath = env->GetStringUTFChars(splitFilePath_, nullptr);
    const char *suffix = env->GetStringUTFChars(suffix_, nullptr);

    // 得到文件大小
    long file_size = getFileSize(splitFilePath);
    Logger("文件大小 == %ld",file_size);

    // 返回char*型指针所占空间：4 (Byte) * fileNum
    char** split_path_list = (char**)malloc(sizeof(char*) * fileNum);

    // 得到路径字符长度
    int file_path_str_len = static_cast<int>(strlen(splitFilePath));
    //动态获取路径（在旧的路径上新增后缀，以示区分）
    std::string str = splitFilePath;
    int position = static_cast<int>(str.find_last_of("."));
    const char *splitPath = str.substr(0,position).c_str();
    Logger("splitPath %s",splitPath);
    //添加新的文件路径（包含文件名称）
    char file_path[file_path_str_len + 5] ;
    strcpy(file_path,splitPath);//复制字符串(目的字符串，源字符串)
    strcat(file_path,"split_%d");//追加字符串
    strcat(file_path,suffix);
    Logger("file_path end %s",file_path);
    for (int i=0; i < fileNum; ++i) {
        // 申请单个文件的路径动态内存存储
        split_path_list[i] = (char*)malloc(sizeof(char) * 128);
        // 组合分割的单个文件路径
        sprintf(split_path_list[i],file_path,(i+1)) ;
        Logger("for split_path_list %s",split_path_list[i]);
    }

    // 读文件
    FILE* fp = fopen(splitFilePath,"rbe");
    if(fp == nullptr) {
        Logger("%s","文件不存在，或文件不可读");
        return;
    }
    // 整除 ， 说明各个文件划分大小一致
    if (file_size % fileNum) {
        // 单个文件大小
        int part_file_size = static_cast<int>(file_size / fileNum);
        Logger("单个文件大小 == %d",part_file_size);
        // 分割多少个文件就分段读多少次
        for (int i = 0; i < fileNum; i++) {
            // 写文件
            FILE* fwp = fopen(split_path_list[i],"wbe");
            if(fwp == nullptr) {
                Logger("%s","FILE is null");
                return;
            }
            // 单个文件有多大 ， 就读写多少次
            for (int j = 0; j < part_file_size; j++) {
                //fgetc读取，并且fputc写入文件
                fputc(fgetc(fp),fwp) ;
            }
            //关闭文件流
            fclose(fwp);
        }
    } else {
        /*文件大小不整除*/
        int part_file_size = static_cast<int>(file_size / (fileNum - 1));
        Logger("单个文件大小 == %d",part_file_size);
        for (int i = 0; i < (fileNum - 1); i++) {
            // 写文件
            FILE* fwp = fopen(split_path_list[i],"wbe");
            if(fwp == nullptr) {
                Logger("%s","FILE is null") ;
                return;
            }
            for (int j = 0; j < part_file_size; j++) {
                fputc(fgetc(fp),fwp);
            }
            // 关闭流
            fclose(fwp);
        }
        // 剩余部分
        FILE* last_fwp = fopen(split_path_list[fileNum - 1],"wbe") ;
        for (int i= 0; i < file_size % (fileNum -1); i++) {
            fputc(fgetc(fp),last_fwp) ;
        }
        // 关闭流
        fclose(last_fwp);
    }

    // 关闭文件流
    fclose(fp);
    // 释放动态内存
    for (int i= 0; i < fileNum ; i++) {
        free(split_path_list[i]) ;
    }
    free(split_path_list);

    env->ReleaseStringUTFChars(splitFilePath_, splitFilePath);
    env->ReleaseStringUTFChars(suffix_, suffix);
}

/** 合并文件 */
extern "C"
JNIEXPORT void JNICALL
Java_com_kotlinstrong_stronglib_cutil_EncryptUtils_fileMerge(JNIEnv *env, jclass type,
                                                       jstring splitFilePath_,
                                                       jstring splitSuffix_,
                                                       jstring mergeSuffix_, jint fileNum) {
    const char *splitFilePath = env->GetStringUTFChars(splitFilePath_, nullptr);
    const char *splitSuffix = env->GetStringUTFChars(splitSuffix_, nullptr);
    const char *mergeSuffix = env->GetStringUTFChars(mergeSuffix_, nullptr);

    char** split_path_list = (char**)malloc(sizeof(char*) * fileNum) ;
    //动态获取路径（在旧的路径上新增后缀，以示区分）
    std::string str = splitFilePath;
    int position = static_cast<int>(str.find_last_of("."));
    const char *basePath = str.substr(0,position).c_str();

    //组装split文件路径
    int split_file_path_len = static_cast<int>(strlen(splitFilePath));
    int split_file_path_suffix_len = static_cast<int>(strlen(splitSuffix));
    char split_file_path[split_file_path_len + split_file_path_suffix_len] ;
    strcpy(split_file_path,basePath);//复制
    strcat(split_file_path,"split_%d");//组装成切割的路径，跟切割对应
    strcat(split_file_path,splitSuffix);//切割的文件后缀

    //组装merge文件路径
    int merge_file_path_len = static_cast<int>(strlen(mergeSuffix));
    char merge_file_path[split_file_path_len + merge_file_path_len] ;
    strcpy(merge_file_path,basePath);//复制
    strcat(merge_file_path,mergeSuffix);//合并后的路径(包含名称)

    Logger("merge 文件路径 = %s",merge_file_path) ;

    //循环得到split文件路径列表
    int file_path_str_len = static_cast<int>(strlen(split_file_path));
    for (int i= 0; i < fileNum; i++) {
        split_path_list[i] = (char*)malloc(sizeof(char) * file_path_str_len) ;
        sprintf(split_path_list[i],split_file_path,(i+1)) ;
        Logger("split文件路径列表 = %s",split_path_list[i]) ;
    }
    //创建并打开 merge file
    FILE* merge_fwp = fopen(merge_file_path,"wbe") ;
    //边读边写，读多个文件，写入一个文件
    for (int i = 0; i < fileNum ; i++) {
        FILE* split_frp = fopen(split_path_list[i],"rbe") ;
        if(split_frp == nullptr) {
            Logger("%s","FILE is null");
            return;
        }
        long part_split_file_size = getFileSize(split_path_list[i]);
        for (int j = 0; j < part_split_file_size; j++) {
            fputc(fgetc(split_frp),merge_fwp);
        }

        // 关闭流
        fclose(split_frp) ;
        // 每合并一个文件 ，就删除它
        remove(split_path_list[i]) ;
    }
    // 关闭文件流
    fclose(merge_fwp);
    // 释放动态内存
    for (int i = 0; i < fileNum; i++) {
        free(split_path_list[i]) ;
    }
    free(split_path_list);
    Logger("%s","文件合并完成") ;

    env->ReleaseStringUTFChars(splitFilePath_, splitFilePath);
    env->ReleaseStringUTFChars(splitSuffix_, splitSuffix);
    env->ReleaseStringUTFChars(mergeSuffix_, mergeSuffix);
}

