package com.hll_sc_app.base;

import androidx.lifecycle.LifecycleOwner;

/**
 * <br>
 * <b>功能：</b>MVP中View层协议<br>
 * <b>作者：</b>HuYongcheng<br>
 * <b>日期：</b>2016/10/11<br>
 */
public interface IView {

    boolean isActive();

    LifecycleOwner getOwner();

}
