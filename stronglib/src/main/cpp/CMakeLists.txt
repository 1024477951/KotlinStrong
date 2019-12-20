# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# 生成动态库(so)
add_library(
        # 生成的so库名称
        Encrypt
        # Sets the library as a shared library.
        # STATIC：静态库，是目标文件的归档文件，在链接其它目标的时候使用
        # SHARED：动态库，会被动态链接，在运行时被加载
        # MODULE：模块库，是不会被链接到其它目标中的插件，但是可能会在运行时使用dlopen-系列的函数动态链接
        SHARED
        # 资源文件
        Encrypt.cpp)
add_library(Signature SHARED Signature.cpp)

# 从系统查找链接依赖库
find_library(
        # log库存放的位置，在log-lib中
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)
#将查找到的log库设置到我们的动态库里
target_link_libraries(Encrypt ${log-lib})
target_link_libraries(Signature ${log-lib})