package com.hll_sc_app.base.bean;


import android.text.TextUtils;

import java.util.LinkedHashMap;

/**
 * 构造请求参数
 *
 * @author zhuyingsong
 * @date 2019/4/23
 */
public class BaseMapReq {
    private LinkedHashMap<String, String> data;

    private BaseMapReq() {
        data = new LinkedHashMap<>();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private void put(String key, String value) {
        put(key, value, false);
    }

    private void put(String key, String value, boolean nullable) {
        if (nullable) {
            if (value == null) value = "";
            data.put(key, value);
        } else if (!TextUtils.isEmpty(value)) data.put(key, value);
    }

    private String get(String key) {
        return data.get(key);
    }

    public LinkedHashMap<String, String> getData() {
        return data;
    }

    public static class Builder {
        private BaseMapReq mReq;

        Builder() {
            mReq = new BaseMapReq();
        }

        public Builder put(String key, String value) {
            mReq.put(key, value);
            return this;
        }

        public Builder put(String key, String value, boolean nullable) {
            mReq.put(key, value, nullable);
            return this;
        }

        public BaseMapReq create() {
            return mReq;
        }
    }
}
