# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Dialog
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends org.greenrobot.eventbus.**

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses,EnclosingMethod
-dontoptimize

# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class ** {
    public void onUpdateEvent(**);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-dontwarn android.os.**
-dontwarn com.android.internal.**
-dontwarn android.support.v4.**
-dontwarn android.support.v7.**
-dontwarn com.zhihu.matisse.**

-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.**{*;}

# retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

# okhttp3
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-dontwarn okio.**
-keep class okio.**{*;}

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

-dontwarn de.greenrobot.common.**
-keep class de.greenrobot.common.**{*;}

-dontwarn org.joda.time.**
-keep public class org.joda.tim.**{*;}

# MPAndroidChart
-dontwarn com.github.mikephil.charting.**
-keep class com.github.mikephil.charting.** { *; }
-dontwarn android.media.MediaMetadataRetriever

# 二十二城
-dontwarn com.hll_sc_app.bean.**
-keep class com.hll_sc_app.bean.**{*;}
-keep class com.hll_sc_app.base.bean.**{*;}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.squareup.picasso.**
# banner 的混淆代码
-keep class com.youth.banner.** { *; }

# AndPermission
-dontwarn com.yanzhenjie.permission.**

# BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {*;}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder { <init>(...);}

# Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

# 阿里云推送
-keepclasseswithmembernames class ** { native <methods>;}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-keep class com.ut.** {*;}
-keep class com.ta.** {*;}
-keep class anet.**{*;}
-keep class anetwork.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-keep class android.os.**{*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**
-dontwarn anetwork.**
-dontwarn com.ut.**
-dontwarn com.ta.**
# ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# 3D 地图 V5.0.0之后：
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.amap.api.trace.**{*;}
# 定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
# 搜索
-keep class com.amap.api.services.**{*;}

# Gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.qiancheng.carsmangersystem.**{*;}

# GreenDAO
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {public static java.lang.String TABLENAME;}
-keep class **$Properties
-keep class org.greenrobot.greendao.**{*;}
-dontwarn org.greenrobot.greendao.database.**
-dontwarn rx.**

-dontwarn com.google.auto.service.processor.**
-dontwarn javax.**
-dontwarn javax.annotation.processing.**
-dontwarn com.bumptech.glide.**
-dontwarn org.conscrypt.**
-dontwarn org.codehaus.mojo.animal_sniffer.**

-keep class com.hualala.citymall.wigdet.daterange.CustomRangeMonthView {
    public <init>(android.content.Context);
}
-keep class com.hualala.citymall.wigdet.daterange.CustomWeekBarView {
    public <init>(android.content.Context);
}
# aurora-imui
-keep class cn.jiguang.imui.** { *; }

# 微信
-keep class com.tencent.mm.opensdk.** {
     *;
}
-keep class com.tencent.mm.sdk.** {
     *;
}

# smack
-dontwarn org.jivesoftware.**
-keep class org.jivesoftware.smack.** { *;}
-keep class org.jivesoftware.smackx.** { *;}

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

# tinker混淆规则
-dontwarn com.tencent.tinker.**
-keep class com.tencent.tinker.** { *; }
