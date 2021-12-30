package com.config

object Switch {
    //#0=生产环境|1=测试环境
    const val workType = 0

    const val isX86 = false
}

object Versions {
    const val compileSdk = 31
    const val buildTools = "31.0.0"
    const val minSdk = 23
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0"

    const val gradle = "7.0.3"
    const val kotlin = "1.5.21"
    const val core_ktx = "1.3.2"

    const val appcompat = "1.2.0"
    const val material = "1.3.0"
    const val constraintLayout = "2.0.4"
    const val fragment = "1.3.0-rc02"
    const val navigation = "2.3.2"
    const val recyclerView = "1.1.0"
    const val lifecycle = "2.2.0"
    const val viewmodel = "2.0.1"
    const val compose = "1.0.1"
    const val composeActivity = "1.4.0"

    const val mmkv = "1.2.7"
    const val blankjUtils = "1.30.4"
    const val pictureSelector = "v2.6.0"

    const val okhttp = "4.9.3"
    const val retrofit = "2.9.0"
    const val gson = "2.8.6"
    const val cookie = "1.0.1"

    const val cymChad = "3.0.2"
    const val smart = "1.1.2"
    const val glide = "4.11.0"
    const val transformations = "4.2.0"

    const val aspectjx_plugin = "2.3"
    const val aspectjx = "1.9.6"

    const val banner = "2.1.0"
    const val immersion = "3.0.0"
    const val noober = "1.6.5"

    const val liveBus = "1.8.0"

}

object Accompanist {
    const val version = "0.21.3-beta"
    const val insets = "com.google.accompanist:accompanist-insets:$version"
    const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:$version"
    const val flowlayouts = "com.google.accompanist:accompanist-flowlayout:$version"
}
