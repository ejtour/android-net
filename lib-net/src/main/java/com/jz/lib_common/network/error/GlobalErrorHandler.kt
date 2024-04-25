package com.jz.lib_common.network.error

import com.jz.lib_common.network.core.HttpResponseCallAdapterFactory
import com.jz.lib_common.utils.LogUtils

/**
 * 全局的错误请求拦截处理,比如Token 过期实现之类的问题，要立马跳转到登录页面
 *
 *  后期根据业务场景完善
 */
open class GlobalErrorHandler : HttpResponseCallAdapterFactory.ErrorHandler{
    override fun onFailure(throwable: BusinessException) {
        LogUtils.d("GlobalErrorHandler","全局错误 ${throwable.code}  需根据业务场景完善")
        when (throwable.code) {
            101 -> {


            }
            102 -> {
                LogUtils.d("GlobalErrorHandler","")
            }
        }
    }
}