# KotlinStrong
this is a strong's kotlin app
自己学习kotlin所封装的一个网络访问框架，mvvm模式（livedata + databinding），base抽离成了lib通用库
lib中的网络访问对外扩展方法（拦截器）,代码中大量注释和见解

![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/home.gif)

#### 学习笔记
#
    常用关键字
        添加注解 @JvmStatic、@JvmField 的作用是将private变为public
        object：有三个用途
            1.对象表达式
                可以实现Java中匿名内部类的效果
            2.对象声明
                作用等同于class，同时还会生成该类的单例对象（注意该类的构造器已经私有，不能被继承）
            3.伴生对象（companion object）
                跟当前类相伴产生的，在Kotlin中是没有static关键字的，所以通常使用伴生对象来达成一样的作用
                class A{
                    companion object 伴生对象名{
                        // 伴生对象名是可以省略
                    }
                }
#
#### LiveData
#
    LiveData可以通知指定某个字段的数据更新.
    MutableLiveData整个实体类或者数据类型变化后才通知.
        postValue():可以在其他线程中调用,主线程执行发布的任务之前多次调用此方法，则仅将分配最后一个值,
                如果同时调用 .postValue(“a”)和.setValue(“b”)，一定是值b被值a覆盖
        setValue():此方法只能在主线程里调用
            
        observe:关联生命周期,只在活跃状态下更新数据，就是在 onStart()、onPause()、onResume() 这三个生命周期下会回调数据更新
        observeForever:不会被自动删除,需要手动调用removeObserver以停止，一直处于活动状态，不管是否在前台
#
#### SAM 转换
#
   Single Abstract Method Conversions，只有单个非默认抽象方法接口的转换（在 Java8 中也有这种机制）
    view.setOnClickListener{
        println("click")
    }
    在 Kotlin 中，一个 Lambda 就是一个匿名函数，Lambda 作为参数，java 中没有高阶函数的支持。
    在 java 中需要使用是传递一个匿名类作为参数，然后在实现抽象方法
    Java代码依赖于SAM转换，使用单个抽象方法将lambda自动转换为接口。Kotlin中定义的接口当前不支持SAM转换。需要定义一个实现该接口的匿名对象
    addEvent(BR.click, object : Function<Article> {
    Java代码依赖于SAM转换，使用单个抽象方法将lambda自动转换为接口。目前 Kotlin中定义的接口不支持 SAM转换。相反，需要定义实现接口的匿名对象
#
#### AOP AspectJ
博客地址：[https://www.cnblogs.com/LiuZhen/p/11851590.html](https://www.cnblogs.com/LiuZhen/p/11851590.html)
#
    AspectJ是一个面向切面编程的一个框架，它扩展了java语言，并定义了实现AOP的语法。
    在将.java文件编译为.class文件时默认使用javac编译工具，AspectJ会有一套符合java字节码编码规范的编译工具来替代javac，在将.java文件编译为.class文件时，会动态的插入一些代码来做到对某一类特定东西的统一处理。

    介绍：通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的技术。对业务逻辑的各个部分进行隔离，耦合度降低，提高程序的可重用性，同时提高了开发的效率。
    OOP（面向对象编程）针对业务处理过程的实体及其属性和行为进行抽象封装，以获得更加清晰高效的逻辑单元划分，而AOP则是针对业务处理过程中的切面进行提取，它所面对的是处理过程中的某个步骤或阶段，以获得逻辑过程中各部分之间低耦合性的隔离效果。
    AOP编程的主要用途有：日志记录，行为统计，安全控制，事务处理，异常处理，系统统一的认证、权限管理等。
    AspectJ的配置很麻烦，这里使用框架AspectJX
#
#### jni

cpp文件加密：[https://www.cnblogs.com/LiuZhen/p/12024257.html](https://www.cnblogs.com/LiuZhen/p/12024257.html)

文件切割合并：[https://www.cnblogs.com/LiuZhen/p/12120618.html](https://www.cnblogs.com/LiuZhen/p/12120618.html)

#
    Encrypt 加密
    Signature 签名
#
#### 协程
#
    协程是通过编译技术来实现的（不需要虚拟机VM/操作系统OS的支持），通过插入相关代码来生效。与之相反，线程/进程是需要虚拟机VM/操作系统OS的支持，
    通过调度CPU执行生效,协程挂起几乎无代价，无需上下文切换或涉及OS。最重要的是协程挂起可由用户控制：可决定挂起时发生什么，并根据需求优化/记录日志/拦截
    协程并不是为了取代线程，协程对线程进行抽象，你可以看成协程是一个异步调用的框架.
    线程和协程的目的本质上存在差异:
        1、线程的目的是提高CPU资源使用率, 使多个任务得以并行的运行, 所以线程是为了服务于机器的.
        2、协程的目的是为了让多个任务之间更好的协作, 主要体现在代码逻辑上, 所以协程是为了服务于人的, 写代码的人. (也有可能结果会能提升资源的利用率, 但并不是原始目的)
    在调度上, 协程跟线程也不同:
        1、线程的调度是系统完成的, 一般是抢占式的, 根据优先级来分配, 是空分复用.
        2、协程的调度是开发者根据程序逻辑指定好的, 在不同的时期把资源合理的分配给不同的任务, 是时分复用的.

    Coroutine：协程的执行其实是断断续续的: 执行一段, 挂起来, 再执行一段，协程创建后, 并不总是立即执行, 要分是怎么创建的协程,
               通过launch方法的第二个参数是一个枚举类型CoroutineStart, 如果不填, 默认值是DEFAULT, 那么协程创建后立即启动,
               如果传入LAZY, 创建后就不会立即启动, 直到调用Job的start方法才会启动.

    Coroutine dispatchers ：协程调度
              协程是运行在线程上的，获取数据后要更新 UI ，但是在 Android 里更新 UI 只能在主线程，在子线程里获取数据，然后在主线程里更新 UI。然后改变协程的运行线程，这就是 Coroutine dispatchers 的功能了。
              [ Coroutine dispatchers 可以指定协程运行在 Android 的哪个线程里
                Dispatchers.Default	共享后台线程池里的线程
                Dispatchers.Main	Android主线程
                Dispatchers.IO	共享后台线程池里的线程
                Dispatchers.Unconfined	不限制，使用父Coroutine的现场
                newSingleThreadContext	使用新的线程 ]

    suspend : suspend 挂起，主线程中调用接口执行到withContext时，withContext代码块将被挂起，然后执行后续代码，而挂起函数等于开了一个协程，执行成功后返回
              suspend 方法能够使协程执行暂停，等执行完毕后在返回结果，同时不会阻塞线程。
              uspend修饰的方法只能在协程里面调用，编译后会增加一个 Continuation 的入参，用于实现回调，然后会在方法里面生成一个 switch 状态机，
              suspend方法的本质是异步返回(注意: 不是异步回调).就是将其拆成 “异步” + “返回”，首先, 数据不是同步回来的(同步指的是立即返回),
              而是异步回来的.其次, 接受数据不需要通过callback, 而是直接接收返回值.

    fun test(param: String,cont: Continuation): Any?{
            val sm = cont as? ThisSM ?: object : ThisSM{
                ...
            }
            switch(sm.label){
                case 0:
                    sm.item = param
                    sm.label = 1
                    return requestToken(sm)
                case 1:
                    val item = sm.item
                    val token = sm.result as Token
                    sm.label = 2
                    return createPost(token, item, sm)
                case 2:
                    val post = sm.result as Post
                    sm.label = 3
                    return processPost(post, sm)
                case 3:
                    return sm.result as PostResult
            }
    }
    协程（Coroutine）的运行依赖于各种 Callback 机制，也就是说一个 suspend 方法调用到最后其实就是注册一个回调.
    编译器会为这个 suspending 方法生产一个类型为 Continuation 的匿名内部类（扩展 CoroutineImpl），用于对这个 suspending 方法自身的回调,
    并且这个类会被传递给这个 suspending 方法所调用的其它 suspending 方法，这些子方法可以通过 Continuation 回调父方法以恢复暂停的程序
    最后，这个 suspending 方法如果调用其它 suspending 方法，会将这些调用转换为一个 switch 形式的状态机，每个 case 表示对一个 suspending 子方法的调用,
    或最后的 return。同时，生成的 Continuation 匿名内部类会保存下一步需要调用的 suspending 方法的 label 值，
    表示应该执行 switch 中的哪个 case，从而串联起整个调用过程
#
#
     * 应用启动流程
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
#
