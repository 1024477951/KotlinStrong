# KotlinStrong
this is a strong's kotlin app
自己学习kotlin所封装的一个网络访问框架，mvvm模式（livedata + databinding），base抽离成了lib通用库，继承就能使用，方便的很
代码中大量注释和见解，还包括新特性（比如协程）的介绍原理
适配器也是自己封装的,DiffUtil差异刷新，BaseDataBinding视图事件，lib中的网络访问对外扩展方法（拦截器）

![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/home.gif)

#
    #学习笔记（以下是看了很多资料及文档和大神的博客在总结出来的）#

   <!-- SAM 转换： Single Abstract Method Conversions，只有单个非默认抽象方法接口的转换（在 Java8 中也有这种机制）-->
    view.setOnClickListener{
        println("click")
    }
    在 Kotlin 中，一个 Lambda 就是一个匿名函数，Lambda 作为参数，java 中没有高阶函数的支持。
    在 java 中需要使用是传递一个匿名类作为参数，然后在实现抽象方法

    addEvent(BR.click, object : Function<Article> {
    Java代码依赖于SAM转换，使用单个抽象方法将lambda自动转换为接口。目前 Kotlin中定义的接口不支持 SAM转换。相反，需要定义实现接口的匿名对象

    <!-- 协程 -->
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

    suspend : ssuspend 方法能够使协程执行暂停，等执行完毕后在返回结果，同时不会阻塞线程。
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

