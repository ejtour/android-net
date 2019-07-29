package com.hll_sc_app.utils;

/**
 * 元组
 *
 * @author zhuyingsong
 * @date 2019-07-26
 */
public class Tuple<KEY1, KEY2> {
    private KEY1 key1;
    private KEY2 key2;


    public KEY1 getKey1() {
        return key1;
    }

    public void setKey1(KEY1 key1) {
        this.key1 = key1;
    }

    public KEY2 getKey2() {
        return key2;
    }

    public void setKey2(KEY2 key2) {
        this.key2 = key2;
    }
}
