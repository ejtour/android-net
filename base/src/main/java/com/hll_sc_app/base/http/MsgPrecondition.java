package com.hll_sc_app.base.http;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.MsgWrapper;

import io.reactivex.functions.Function;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/20
 */

public class MsgPrecondition<T> implements Function<BaseResp<T>, MsgWrapper<T>> {

    @Override
    public MsgWrapper<T> apply(BaseResp<T> response) {
        if (!response.isSuccess()) {
            throw new UseCaseException(response.getCode(), response.getMessage());
        }
        return new MsgWrapper<>(response.getData(), response.getMessage());
    }
}
