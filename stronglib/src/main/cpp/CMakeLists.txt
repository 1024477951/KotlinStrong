# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# 生成动态库
add_library( # Sets the name of the library.
             encrypt
             # Sets the library as a shared library.
             SHARED
             # Provides a relative path to your source file(s).
             encrypt.cpp)
add_library( # Sets the name of the library.
             signature
             # Sets the library as a shared library.
             SHARED
             # Provides a relative path to your source file(s).
             signature.cpp)
#查找log库链接
find_library( # Sets the name of the path variable.
              log-lib
              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )
#将查找到的log库设置到我们的动态库里
target_link_libraries( # Specifies the target library.
                       encrypt
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
target_link_libraries(signature
                      ${log-lib} )