package com.hll_sc_app.impl;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public interface IChangeListener {
    void onChanged();

    default void onChanged(Object o) {
    }
}
