package com.jz.lib_common.network.interceptor

import com.jz.lib_common.network.NetInfo
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 通用的请求拦截器，
 */
open class CommonRequestInterceptor(val requiredInfo: NetInfo?) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("X-Version", requiredInfo?.getAppVersionCode() ?: "0")
        builder.addHeader("X-Platform", "Android")
        builder.addHeader("Connection", "Keep-Alive")
        builder.addHeader("User-Agent", "yourAppLabel-Android")
        return chain.proceed(builder.build())
    }

}