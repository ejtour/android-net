package com.jz.lib_common.network.error

import com.jz.lib_common.network.core.HttpResponseCallAdapterFactory

/**
 * 全局错误的代理
 */
class GlobalErrorProxy() : IGlobalError, HttpResponseCallAdapterFactory.ErrorHandler {

    private var globalError: IGlobalError? = null
    fun initGlobalError(globalError: IGlobalError) {

        this.globalError = globalError;

    }

    override fun onFailure(throwable: BusinessException) {
        globalError?.onFailure(throwable)
    }
}