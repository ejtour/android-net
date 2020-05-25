package com.hll_sc_app.bean.event;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/15
 */

public class SingleListEvent<T> {
    List<T> list;
    Class<T> clazz;

    public SingleListEvent(List<T> list, Class<T> clazz) {
        this.list = list;
        this.clazz = clazz;
    }

    public List<T> getList() {
        return list;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
