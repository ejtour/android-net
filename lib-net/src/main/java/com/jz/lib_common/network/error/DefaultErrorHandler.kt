package com.jz.lib_common.network.error

import com.jz.lib_common.utils.LogUtils

/**
 * 默认全局的错误请求拦截处理,比如Token 过期实现之类的问题，要立马跳转到登录页面
 *
 * 后期根据业务场景完善
 */
class DefaultErrorHandler : IGlobalError {
    override fun onFailure(throwable: BusinessException) {
        LogUtils.d("DefaultErrorHandler", "业务自己处理各种错误code =" + throwable.code)
        when (throwable.code) {
            101 -> {


            }
            102 -> {
                LogUtils.d("GlobalErrorHandler","")
            }
        }

    }
}