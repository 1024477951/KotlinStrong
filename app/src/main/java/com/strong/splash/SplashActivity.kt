package com.kotlinstrong.splash

import android.os.Bundle
import android.os.Handler
import com.kotlinstrong.R
import com.kotlinstrong.main.MainActivity
import com.kotlinstrong.stronglib.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun layoutId() = R.layout.activity_splash

    override fun initData(bundle: Bundle?) {
        Handler().postDelayed({
            MainActivity.startMainActivity()
            finish()
        }, 500)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.setBackgroundDrawable(null)
    }
    /**
     * 应用启动流程（此处参考了终端研发部的文章，很全面）
        Launcher startActivity
        AMS startActivity
        Zygote fork 进程
        ActivityThread main()
        4.1. ActivityThread attach
        4.2. handleBindApplication
        4.3 attachBaseContext
        4.4. installContentProviders
        4.5. Application onCreate
        ActivityThread 进入loop循环
        Activity生命周期回调，onCreate、onStart、onResume…
     * 启动优化：
        打包资源文件，生成R.java文件
        编译 java 文件，生成对应.class文件
        .class 文件转换成dex文件
        打包成没有签名的apk（使用工具apkbuilder）
        使用签名工具给apk签名
        对签名后的.apk文件进行对齐处理，不进行对齐处理不能发布到Google Market（使用工具zipalign）
     * 1.闪屏页优化：
            配置主题，设置主题背景
     * 2.MultiDex优化
        Android Studio 编译过程：
            将class文件转换成dex文件，默认只会生成一个dex文件，单个dex文件中的方法数不能超过65536，不然编译会报错
            在5.0以上手机,gradle增加一行配置即可(multiDexEnabled true)
            5.0以下手机运行直接crash，报错 Class NotFound xxx
            Android 5.0以下，ClassLoader加载类的时候只会从class.dex（主dex）里加载，ClassLoader不认识其它的class2.dex、class3.dex、…，
            当访问到不在主dex中的类的时候，就会报错:Class NotFound xxx，因此谷歌给出兼容方案，MultiDex,平均耗时1秒以上
            这里加载的时候会有解压和压缩的操作（涉及到ClassLoader加载类原理），第一次加载才会执行解压和压缩过程，第二次进来读取sp中保存的dex信息，
            直接返回file list，所以第一次启动的时候比较耗时
        安装流程：
            创建一个新的数组，把原来数组内容（主dex）和要增加的内容（dex2、dex3…）拷贝进去，反射替换原来的dexElements为新的数组，
            Tinker热修复的原理也是通过反射将修复后的dex添加到这个dex数组去，不同的是热修复是添加到数组最前面，而MultiDex是添加到数组后面
        MultiDex是怎么把dex2添加进去的：
            5.0以下这个dexElements 里面只有主dex，没有dex2、dex3...，需要反射DexPathList的dexElements字段，然后把我们的dex2添加进去
        今日头条的MultiDex优化方案：
            在Application 的attachBaseContext 方法里，启动另一个进程的LoadDexActivity去异步执行MultiDex逻辑，显示Loading。
            然后主进程Application进入while循环，不断检测MultiDex操作是否完成
            MultiDex执行完之后主进程Application继续走，ContentProvider初始化和Application onCreate方法，也就是执行主进程正常的逻辑。
     * 3.第三方库懒加载
        Application中进行初始化的第三方库，开源库都放在Application中对冷启动会有影响，所以可以考虑按需初始化，
        例如Glide，可以放在自己封装的图片加载类中，调用到再初始化
     * 4.WebView启动优化
        WebView第一次创建比较耗时，可以预先创建WebView，提前将其内核初始化。
        使用WebView缓存池，用到WebView的地方都从缓存池取，缓存池中没有缓存再创建，注意内存泄漏问题。
        本地预置html和css，WebView创建的时候先预加载本地html，之后通过js脚本填充内容部分。
     * 5.数据预加载
        在主页空闲的时候，将其它页面的数据加载好，保存到内存或数据库，等到打开该页面的时候，判断已经预加载过，直接从内存或数据库读取数据并显示
     * 6.线程优化
        线程的频繁创建是耗性能的，所以会用线程池。单个cpu情况下，即使是开多个线程，同时也只有一个线程可以工作，
        所以线程池的大小要根据cpu个数来确定
     **/

}