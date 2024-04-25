package com.jz.lib_common.network.bean

import com.squareup.moshi.Json

/**
 * option + k  自动生成 使用插件 Json To Kotlin Class
 */
open class BusinessBaseResponse {

    //0 代表成功 ，其他代表错误。这个Code 是我们业务定义的和HTTP定义的200，300，400 是不一样的处理方式
    @Json(name = "code")
    val code: Int = 0

    @Json(name = "message")
    val message: String = ""



}