package com.jz.lib_common.network

import android.annotation.SuppressLint
import android.util.ArrayMap
import com.jz.lib_common.network.core.HttpResponseCallAdapterFactory
import com.jz.lib_common.network.core.MoshiResultTypeAdapterFactory
import com.jz.lib_common.network.error.BusinessException
import com.jz.lib_common.network.error.DefaultErrorHandler
import com.jz.lib_common.network.error.GlobalErrorProxy
import com.jz.lib_common.network.error.IGlobalError
import com.jz.lib_common.network.interceptor.CommonRequestInterceptor
import com.jz.lib_common.network.interceptor.CommonResponseInterceptor
import com.jz.lib_common.network.interceptor.SignInterceptor
import com.jz.lib_common.utils.LogUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

/**
 * 网络的封装
 */
abstract class RetrofitApi(private var NET_WORK_BASE_URL: String?)  {

    companion object {

        private var netInfo: NetInfo? = null
        // 缓存已经创建的retrofit 对象
        private var retrofitArrayMap = ArrayMap<String, Retrofit>()
        private var okhttpClient: OkHttpClient? = null
        // 代理模式(将错误交给调用方处理)
        private var globalErrorProxy: GlobalErrorProxy = GlobalErrorProxy()

        fun initNetInfo(netInfo: NetInfo, errorHandler: IGlobalError = DefaultErrorHandler()) {

            this.netInfo = netInfo
            globalErrorProxy.initGlobalError(errorHandler)

        }

    }



    //https://github.com/square/moshi 就是为了替换GSON ，详情查阅相关介绍
    private val moshi = Moshi.Builder()
        //添加返回的json 数据自定义解析器
        .add(MoshiResultTypeAdapterFactory(getHttpWrapperHandler()))
        .addLast(KotlinJsonAdapterFactory()) //
        .build()


    @SuppressLint("SuspiciousIndentation")
    private fun createOkhttp(): OkHttpClient {

        if (okhttpClient == null) {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(SignInterceptor())
            //添加公共拦截器
            okHttpClientBuilder.addInterceptor(CommonRequestInterceptor(netInfo))
            okHttpClientBuilder.addInterceptor(CommonResponseInterceptor())
            if (getInterceptor() != null) {
                //自定义的拦截器
                okHttpClientBuilder.addInterceptor(getInterceptor()!!)
            }

            if (netInfo != null && netInfo!!.showLog()) {
                okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor { message ->
                    LogUtils.d("okHttp", message)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            okhttpClient = okHttpClientBuilder.build()
        }

        return okhttpClient!!
    }

    open fun <T> createRxJava(tClass: Class<T>?): T {

        return getRetrofit(tClass).create(tClass)
    }

    private fun <T> getRetrofit(tClass: Class<T>?): Retrofit {
        val retrofitKey = NET_WORK_BASE_URL + tClass?.simpleName
        retrofitArrayMap[retrofitKey]?.run {
            return this
        }

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(NET_WORK_BASE_URL)
            .client(createOkhttp())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        //全局的错误处理器

        retrofitBuilder.addCallAdapterFactory(
            HttpResponseCallAdapterFactory(globalErrorProxy)
        )
        val retrofit = retrofitBuilder.build()

        retrofitArrayMap[retrofitKey] = retrofit
        return retrofit
    }


    /**
     * 不同的host 除了通用的拦截器，还可以定制化自己的拦截器
     */
    protected abstract fun getInterceptor(): Interceptor?

    /**
     * 定义好字段映射关系
     * 如果能映射到code+msg+data 就去重写本方法，否则就空着
     * 然后就要自己判断业务成功code和对应的处理之类的了
     *
     */
    protected open fun getHttpWrapperHandler(): MoshiResultTypeAdapterFactory.HttpWrapper? {
        return null
    }

}
