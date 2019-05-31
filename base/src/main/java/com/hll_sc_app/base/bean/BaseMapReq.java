package com.hll_sc_app.base.bean;


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
        data.put(key, value);
    }

    private String get(String key) {
        return data.get(key);
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

        public BaseMapReq create() {
            return mReq;
        }
    }
}
