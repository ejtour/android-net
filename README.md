# 二十二城供应商

* 测试包: https://www.pgyer.com/22city_sc_beta
* 正式包: https://www.pgyer.com/22city_sc

# 开发框架
## 该项目采用MVP架构模式

> MVP把Activity中的UI逻辑抽象成View接口，把业务逻辑抽象成Presenter接口，Model类是实体类

* 分离了视图逻辑和业务逻辑，降低了耦合
* Activity只处理生命周期的任务，代码变得更加简洁
* 视图逻辑和业务逻辑分别抽象到了View和Presenter的接口中去，提高代码的可阅读性
* Presenter被抽象成接口，可以有多种具体的实现，所以方便进行单元测试
* 把业务逻辑抽到Presenter中去，避免后台线程引用着Activity导致Activity的资源无法被系统回收从而引起内存泄露和OOM


## 网络请求部分
* Retrofit + RxJava + RxAndroid +okhttp
    * [Retrofit](https://github.com/square/retrofit)

      > 网络加载框架，包含有特别多的注解，方便简化代码量 ，可以配置不同的HttpClient来实现网络请求，支持同步、异步和RxJava,可以配置不同的反序列化工具

    * [RxJava](https://github.com/ReactiveX/RxJava)

      > 是一个实现异步操作的库，它很好的将链式编程风格和异步结合在一起，RxJava的优势是简洁，随着程序逻辑变得越来越复杂，它依然能保持简洁

    * [RxAndroid](https://github.com/ReactiveX/RxAndroid)

      > 起源于RxJava，是一个专门针对Android版本的RxJava库

    * [okhttp](https://github.com/square/okhttp)

      > 一个处理网络请求的开源项目，Square公司开发

## 路由部分
* [arouter](https://github.com/alibaba/ARouter) 阿里巴巴开源路由框架
    * 支持直接解析标准URL进行跳转，并自动注入参数到目标页面中
    * 支持多模块工程使用
    * 支持添加多个拦截器，自定义拦截顺序
    * 支持依赖注入，可单独作为依赖注入框架使用
    * 支持InstantRun
    * 支持MultiDex(Google方案)
    * 映射关系按组分类、多级管理，按需初始化
    * 支持用户指定全局降级与局部降级策略
    * 页面、拦截器、服务等组件均自动注册到框架
    * 支持多种方式配置转场动画
    * 支持获取Fragment
    * 完全支持Kotlin以及混编(配置见文末 其他#5)
    * 支持第三方 App 加固(使用 arouter-register 实现自动注册)
    * 支持生成路由文档
    * 提供 IDE 插件便捷的关联路径和目标类

## 数据持久化
* SharePreference

  > 轻量级的存储类，特别适合用于保存软件配置参数（token）

* [GreenDao](https://github.com/greenrobot/greenDAO)

  >  Greendao是一款用于数据库创建与管理的框架，由于原生SQLite语言比较复杂繁琐，使得不少程序员不得不去学习SQLite原生语言，但是学习成本高，效率低下，所以不少公司致力于开发一款简单的数据库管理框架，较为著名的就有Greendao和ORMLite，但是就数据分析来看，Greendao的效率是高于ORMLite及其他框架的，是目前该行业的领先者。也因为Greendao的使用方法简便，且效率高使得其成为目前使用最为广泛的数据库管理框架，这也是广大程序员的福音

## 使用的开源库
* [ToastUtils（吐司兼容库）](https://github.com/getActivity/ToastUtils)
* [EventBus（事件总线）](https://github.com/greenrobot/EventBus)
* [BaseRecyclerViewAdapterHelper（RecyclerAdapter）](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* [Gson（Json解析）](https://github.com/google/gson)
* [AndPermission（权限申请）](https://github.com/yanzhenjie/AndPermission)
* [status-bar-compat（状态栏颜色处理）](https://github.com/msdx/status-bar-compat)
* [butterknife（依赖注入）](https://github.com/JakeWharton/butterknife)
* [glide（图片加载）](https://github.com/bumptech/glide)
* [banner](https://github.com/youth5201314/banner)
* [matisse（图片选择）](https://github.com/zhihu/Matisse)
* [imgepreviewlibrary（图片预览）](https://github.com/yangchaojiang/ZoomPreviewPicture)
* [leakcanary（内存泄露检测）](https://github.com/square/leakcanary)
* [SmartRefreshLayout（下拉刷新）](https://github.com/scwang90/SmartRefreshLayout)
* [CalendarView（日历）](https://github.com/huanghaibin-dev/CalendarView)
* [FlycoTabLayout（标签栏）](https://github.com/H07000223/FlycoTabLayout)
* [switchbutton（开关）](https://github.com/kyleduo/SwitchButton)
* [flowlayout（流式布局）](https://github.com/hongyangAndroid/FlowLayout)
* [IndexBarLayout（侧边索引）](https://github.com/qdxxxx/IndexBarLayout)
* [MPAndroidChart（图表）](https://github.com/PhilJay/MPAndroidChart)
* [Luban（图片压缩）](https://github.com/Curzibn/Luban)

## 测试打包

1. [Jenkins地址](http://172.16.47.192:8080/job/android-22city-mall-dohko/)
2. [点击立即构建](https://raw.githubusercontent.com/zhuAria/ScreenShot/master/jenkins.png)
3. [构建完成后下载地址](https://www.pgyer.com/22city_sc_beta)

## 线上打包

1. [Jenkins地址](http://172.16.47.192:8080/job/android-22city-mall-release/)
2. [点击立即构建](https://raw.githubusercontent.com/zhuAria/ScreenShot/master/jenkins.png)
3. [构建完成后下载地址](https://www.pgyer.com/22city_sc)


# For Developer
* [开发规范](https://raw.githubusercontent.com/zhuAria/ScreenShot/master/android_guide.png)
* [打包流程](doc/打包流程.md)
