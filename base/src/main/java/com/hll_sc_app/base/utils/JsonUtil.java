package com.hll_sc_app.base.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Json解析工具类
 *
 * @author zhuyingsong
 * @date 2019/1/25
 */
public class JsonUtil {

    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        return Instance.INSTANCE.toJson(object);
    }

    public static <T> List<T> parseJsonList(String json, Class<T> classOfT) {
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement element : array) {
            list.add(Instance.INSTANCE.fromJson(element, classOfT));
        }
        return list;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return Instance.INSTANCE.fromJson(json, classOfT);
    }

    private static class Instance {
        private static final Gson INSTANCE = new Gson();
    }
}
