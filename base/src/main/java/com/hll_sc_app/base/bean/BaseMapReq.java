package com.hll_sc_app.base.bean;


import java.util.LinkedHashMap;

/**
 * 构造请求参数
 *
 * @author zhuyingsong
 * @date 2019/4/23
 */
public class BaseMapReq {
    private LinkedHashMap<String, Object> data;

    private BaseMapReq() {
        data = new LinkedHashMap<>();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private void put(String key, Object value) {
        put(key, value, false);
    }

    private void put(String key, Object value, boolean nullable) {
        if (nullable) {
            if (value == null) value = "";
            data.put(key, value);
        } else if (value != null) {
            if ("String".equals(value.getClass().getSimpleName()) &&
                    value.toString().length() == 0) {
                return;
            }
            data.put(key, value);
        } else {
            data.remove(key);
        }
    }

    public Object get(String key) {
        return data.get(key);
    }

    public String opString(String key) {
        Object value = get(key);
        return value == null ? null : String.valueOf(value);
    }

    public LinkedHashMap<String, Object> getData() {
        return data;
    }

    public static class Builder {
        private BaseMapReq mReq;

        Builder() {
            mReq = new BaseMapReq();
        }

        public Builder put(String key, Object value) {
            mReq.put(key, value);
            return this;
        }

        public Builder put(String key, Object value, boolean nullable) {
            mReq.put(key, value, nullable);
            return this;
        }

        public BaseMapReq create() {
            return mReq;
        }
    }
}
