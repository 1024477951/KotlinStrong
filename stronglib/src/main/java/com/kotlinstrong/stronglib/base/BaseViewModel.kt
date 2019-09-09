package com.kotlinstrong.stronglib.base

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() , LifecycleObserver {

//    private val viewModelJob = SupervisorJob()
//    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    /**

      viewModelScope : 当你取消一个作用域的时候，所有它创建的协程也会被取消。如果你的 ViewModel 即将被销毁，那么它所有的异步工作也必须被停止。
            ViewModel 类通过 HashMap 存储 CoroutineScope 对象，当使用 getTag(JOB_KEY) 方法获取对象不存在时，
           创建一个新的 CoroutineScope 并调用 setTagIfAbsent(JOB_KEY, scope) 方法存储新建的 CoroutineScope 对象。
           ViewModel 被销毁时内部会执行 clear() 方法，在 clear() 方法中遍历调用 closeWithRuntimeException 取消了 viewModelScope
           的协程.

      Coroutine：协程的执行其实是断断续续的: 执行一段, 挂起来, 再执行一段，协程创建后, 并不总是立即执行, 要分是怎么创建的协程,
                    通过launch方法的第二个参数是一个枚举类型CoroutineStart, 如果不填, 默认值是DEFAULT, 那么协程创建后立即启动,
                    如果传入LAZY, 创建后就不会立即启动, 直到调用Job的start方法才会启动.
        Coroutine dispatchers ：协程调度
            我们已经知道协程是运行在线程上的，我们获取数据后要更新 UI ，但是在 Android 里更新 UI 只能在主线程，所以我们要在子线程里获取数据，然后在主线程里更新 UI。这就需要改变协程的运行线程，这就是 Coroutine dispatchers 的功能了。
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
     */

    private val mException: MutableLiveData<Exception> = MutableLiveData()
    /**
      最后一个入参 block 是一个 suspending Lambda,同 suspending 方法一样，suspending Lambda 在编译之后，
      其主体部分也会被转换为 switch 形式的状态机。不同于对 suspending 方法的处理，编译器并没有为 suspending Lambda 生产类型为 Continuation 的匿名内部类，
      而是 Lambda 自己作为 Continuation 实现（每个 Lambda 在编译之后会生成一个匿名内部类）。
     */
    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }
    /**  withContext : 切换协程
     *   CoroutineScope : 协程的作用域，可以管理其域内的所有协程
     * */
    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }


    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
                           finallyBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

    fun Context.openBrowser(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).run { startActivity(this) }
    }

    suspend fun executeResponse(response: BaseResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                                errorBlock: suspend CoroutineScope.() -> Unit) {
        coroutineScope {
            if (response.code != 200)
                errorBlock()
            else
                successBlock()
        }
    }

    /**
     * 协程是通过编译技术来实现的（不需要虚拟机VM/操作系统OS的支持），通过插入相关代码来生效。与之相反，线程/进程是需要虚拟机VM/操作系统OS的支持，
     * 通过调度CPU执行生效,协程挂起几乎无代价，无需上下文切换或涉及OS。最重要的是协程挂起可由用户控制：可决定挂起时发生什么，并根据需求优化/记录日志/拦截
     * 协程并不是为了取代线程，协程对线程进行抽象，你可以看成协程是一个异步调用的框架.
     *
       线程和协程的目的本质上存在差异:
        线程的目的是提高CPU资源使用率, 使多个任务得以并行的运行, 所以线程是为了服务于机器的.
        协程的目的是为了让多个任务之间更好的协作, 主要体现在代码逻辑上, 所以协程是为了服务于人的, 写代码的人. (也有可能结果会能提升资源的利用率, 但并不是原始目的)
      在调度上, 协程跟线程也不同:
        线程的调度是系统完成的, 一般是抢占式的, 根据优先级来分配, 是空分复用.
        协程的调度是开发者根据程序逻辑指定好的, 在不同的时期把资源合理的分配给不同的任务, 是时分复用的.
     * */

}