package com.jz.lib_common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 签名拦截
 */
class SignInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {

       return chain.proceed(chain.request())
    }
}