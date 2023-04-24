# KotlinStrong
个人日常测试项目  
基于 mvvm,代码中大量注释和见解
gradle config：buildSrc

#### jni
cpp文件加密：[https://www.cnblogs.com/LiuZhen/p/12024257.html](https://www.cnblogs.com/LiuZhen/p/12024257.html)  
文件切割合并：[https://www.cnblogs.com/LiuZhen/p/12120618.html](https://www.cnblogs.com/LiuZhen/p/12120618.html)

#### 贝塞尔波浪
![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/water.gif)

#### Fragment result api
![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/fragment_tab_child.gif)

#### 启动页 SplashFragment
![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/splash.gif)

#### 屏幕适配插件笔记
[https://www.cnblogs.com/LiuZhen/p/14367974.html](https://www.cnblogs.com/LiuZhen/p/14367974.html)

#### FoldTextView
![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/foldtextview.gif)

#### EllipsizeEndTextview
![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/ellipsizetextview.gif)

#### 圆形进度条 CircleProgressView
![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/circleprogress.gif)

#### 欢迎页轮播动画 TextTranslationXGuideView
![image](https://github.com/1024477951/KotlinStrong/blob/master/gif/guide.gif)

#### AOP AspectJ
博客地址：[https://www.cnblogs.com/LiuZhen/p/11851590.html](https://www.cnblogs.com/LiuZhen/p/11851590.html)
#
    AspectJ是一个面向切面编程的一个框架，它扩展了java语言，并定义了实现AOP的语法。
    在将.java文件编译为.class文件时默认使用javac编译工具，AspectJ会有一套符合java字节码编码规范的编译工具来替代javac，在将.java文件编译为.class文件时，会动态的插入一些代码来做到对某一类特定东西的统一处理。

    介绍：通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的技术。对业务逻辑的各个部分进行隔离，耦合度降低，提高程序的可重用性，同时提高了开发的效率。
    OOP（面向对象编程）针对业务处理过程的实体及其属性和行为进行抽象封装，以获得更加清晰高效的逻辑单元划分，而AOP则是针对业务处理过程中的切面进行提取，它所面对的是处理过程中的某个步骤或阶段，以获得逻辑过程中各部分之间低耦合性的隔离效果。
    AOP编程的主要用途有：日志记录，行为统计，安全控制，事务处理，异常处理，系统统一的认证、权限管理等。
    AspectJ的配置很麻烦，大多数会使用框架AspectJX，不过在kotlin中，aspectjx包重复，各种zip is empty，然后资料中无非就是更新版本，或者exclude去重，还有的说把插件名称换了，
    更改为com.github.franticn:gradle_plugin_android_aspectjx，但是都没用，各种Circular dependency between the following tasks，或者A problem occurred evaluating project，
    加上作者目前没更新，所以这里选择另外一种方案，是在AspectJX的issues中发现的，https://github.com/2017398956/AspectPluginDemo
#
#
    Encrypt 加密
    Signature 签名
#
