package com.hll_sc_app.impl;

import com.hll_sc_app.base.ILoadView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public interface IExportView extends ILoadView {
    /**
     * 绑定邮箱
     */
    void bindEmail();

    /**
     * 导出成功
     *
     * @param email 邮箱
     */
    void exportSuccess(String email);

    /**
     * 导出失败
     *
     * @param msg 失败消息
     */
    void exportFailure(String msg);
}
