import com.config.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
}

android {
    namespace = "com.strong.baselib"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(fileTree("libs").include("*.jar", "*.aar"))
    api("androidx.core:core-ktx:${Versions.core_ktx}")

    //google
    api("androidx.appcompat:appcompat:${Versions.appcompat}")
    api("com.google.android.material:material:${Versions.material}")
    api("androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}")
    api("androidx.fragment:fragment-ktx:${Versions.fragment}")
    //mmkv
    api("com.tencent:mmkv-static:${Versions.mmkv}")
    //utils
    api("com.blankj:utilcodex:${Versions.blankjUtils}")
    //gson
    api("com.google.code.gson:gson:${Versions.gson}")
    //沉浸式 （ktx为kotlin扩展，components为fragment扩展）
    api("com.gyf.immersionbar:immersionbar:${Versions.immersion}")
    api("com.gyf.immersionbar:immersionbar-ktx:${Versions.immersion}")
    api("com.gyf.immersionbar:immersionbar-components:${Versions.immersion}")
    //navigation
    api("androidx.navigation:navigation-fragment-ktx:${Versions.navigation}")
    api("androidx.navigation:navigation-ui-ktx:${Versions.navigation}")
    //viewmodel
    api("androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}")
    //okhttp
    api("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}")
    //retrofit
    api("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    api("com.squareup.retrofit2:converter-gson:${Versions.retrofit}")
    api("com.github.franmontiel:PersistentCookieJar:${Versions.cookie}")
    //cymChad
    api("com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.cymChad}")
    //smart
    api("com.scwang.smartrefresh:SmartRefreshLayout:${Versions.smart}")
    api("com.scwang.smartrefresh:SmartRefreshHeader:${Versions.smart}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}