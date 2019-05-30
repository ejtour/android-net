package com.hll_sc_app.base.http;


import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseResp;

import io.reactivex.functions.Function;

/**
 * 返回结果预处理
 *
 * @author zhuyingsong
 * @date 2018/12/14
 */
public final class Precondition<T> implements Function<BaseResp<T>, T> {

    @Override
    public T apply(BaseResp<T> response) {
        if (!response.isSuccess()) {
            throw new UseCaseException(response.getCode(), response.getMessage());
        }
        return response.getData();
    }
}
