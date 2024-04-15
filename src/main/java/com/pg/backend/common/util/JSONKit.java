package com.pg.backend.common.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

import static com.alibaba.fastjson2.JSONWriter.Feature.*;

/**
 * @author paul 2024/4/9
 */

public class JSONKit {
    /*
     * fastjson2 property url: <a href="https://alibaba.github.io/fastjson2/features_cn">...</a>
     * SerializeFeature 在fastjson2 中被移除 代替的是 JSONWriter JSONReader
     */
    private static final JSONWriter.Feature[] WRITER_FEATURES = {
            //序列化输出空值字段
            WriteNulls,
            //List序列化空值输出为空数组"[]"
            WriteNullListAsEmpty,
            //Number序列化空值输出为空字符串""
            WriteNullStringAsEmpty,
            //Boolean序列化空值输出为 false
            WriteNullBooleanAsFalse,
            //数组类型length == 0 时,不输出
            NotWriteEmptyArray
    };
    public static JSONArray convert(String k, String ... values) {
        JSONArray jsonArray = new JSONArray();
        for (String value : values) {
            JSONObject entry = new JSONObject();
            entry.put(k,value);
            jsonArray.add(entry);
        }
        return jsonArray;
    }
    public static JSONObject convert(String k,Object v) {
        return JSONObject.of(k,v);
    }
    public static JSONObject build() {
        return new JSONObject();
    }
}