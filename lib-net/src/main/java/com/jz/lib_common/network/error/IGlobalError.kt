package com.jz.lib_common.network.error

import com.jz.lib_common.network.core.HttpResponseCallAdapterFactory

/**
 * 全局错误处理
 * 期望是交给引用方自行实现 不同的引用方实现错误处理不一定相同 !
 *
 *       IGlobalError
 *
 *  业务方 实现IGlobalError      库内部代理 Re
 */
interface IGlobalError {
     fun onFailure(throwable: BusinessException)
}