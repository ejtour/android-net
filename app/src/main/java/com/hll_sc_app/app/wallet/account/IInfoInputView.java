package com.hll_sc_app.app.wallet.account;

import android.view.View;

import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.wallet.BankBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public interface IInfoInputView {
    /**
     * 页面初始化数据
     *
     * @param info 认证信息
     */
    void initData(AuthInfo info);

    /**
     * 显示图片
     *
     * @param url 图片url
     */
    void setImageUrl(String url);

    /**
     * @return 页面标题
     */
    String getTitle();

    /**
     * 提交监听
     *
     * @param listener 提交监听
     */
    void setCommitListener(View.OnClickListener listener);


    /**
     * 显示银行卡信息
     *
     * @param bean 银行卡信息
     */
    default void setBankData(BankBean bean) {

    }

    /**
     * @return 输入有效性
     */
    boolean verifyValidity();

    /**
     * 将输入的数据填充到认证实体类中
     */
    void inflateInfo();
}
