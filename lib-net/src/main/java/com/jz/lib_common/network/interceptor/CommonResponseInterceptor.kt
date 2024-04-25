package com.jz.lib_common.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class CommonResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestTime = System.currentTimeMillis()

        //只能有一次chain.proceed
        val response = chain.proceed(chain.request())

        //Token 过期，解密，通用错误拦截等等

        return response
    }
}