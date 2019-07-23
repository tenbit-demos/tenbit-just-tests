package cn.tenbit.tests.test02;

import cn.tenbit.hare.core.lite.util.HareLogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;

/**
 * @Author bangquan.qian
 * @Date 2019-07-02 15:46
 */
public class Main {

    @Test
    public void test() {
        Persion persion = Persion.builder()
                .id(1L)
                .name("  haha  ")
                .tags(Lists.<String>newArrayList(
                        "      a   aa   ",
                        "   sd  sd   "
                ))
                .build();

        Object obj = trimServiceInput(persion);
        System.out.println(JSON.toJSONString(obj));
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Persion {
        private Long id;
        private String name;
        private List<String> tags;
    }

    private Object trimServiceInput(Object serviceInput) {
        Object trimServiceInput = serviceInput;
        if (serviceInput == null) {
            return trimServiceInput;
        }
        if (serviceInput instanceof String) {
            return trimServiceInput;
        }
        try {
            trimServiceInput = doTrimServiceInput(trimServiceInput);
        } catch (Throwable e) {
            HareLogUtils.error("trimServiceInput", e);
            trimServiceInput = serviceInput;
        }
        return trimServiceInput;
    }

    private Object doTrimServiceInput(Object obj) {
        if (obj == null) {
            return obj;
        }
        if (obj instanceof String) {
            return obj;
        }
        String jsonString = JSON.toJSONString(obj);
        if (StringUtils.isBlank(jsonString)) {
            return obj;
        }
        return doTrimJsonObject(JSON.parseObject(jsonString)).toJavaObject(obj.getClass());
    }

    private JSONObject doTrimJsonObject(JSONObject jsonObject) {
        JSONObject trimJsonObject = new JSONObject();
        for (String key : jsonObject.keySet()) {
            Object val = jsonObject.get(key);
            if (val == null) {

            } else if (val instanceof String) {
                val = StringUtils.trim((String) val);
            } else if (val instanceof JSONObject) {
                val = doTrimJsonObject((JSONObject) val);
            } else if (val instanceof JSONArray) {
                val = doTrimJsonArray((JSONArray) val);
            }
            trimJsonObject.put(StringUtils.trim(key), val);
        }
        return trimJsonObject;
    }

    private Object doTrimJsonArray(JSONArray jsonArray) {
        if (jsonArray == null) {
            return jsonArray;
        }
        JSONArray trimJsonArray = new JSONArray();
        for (Object obj : jsonArray) {
            Object val = obj;
            if (val == null) {

            } else if (val instanceof String) {
                val = StringUtils.trim((String) val);
            } else if (val instanceof JSONObject) {
                val = doTrimJsonObject((JSONObject) val);
            } else if (val instanceof JSONArray) {
                val = doTrimJsonArray((JSONArray) val);
            }
            trimJsonArray.add(val);
        }
        return trimJsonArray;
    }
}
