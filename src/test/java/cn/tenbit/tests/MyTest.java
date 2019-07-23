package cn.tenbit.tests;

import cn.tenbit.hare.core.lite.util.HarePrintUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author bangquan.qian
 * @Date 2019-07-07 17:08
 */
public class MyTest {

    @Test
    public void test() {
        LocalDate now = LocalDate.now();
        LocalDate date = now.minusDays(1893);
        HarePrintUtils.console(date);
    }

    @Test
    public void test1() {
        String str = "52.00";
        String str1 = "52";
        BigDecimal bigDecimal = new BigDecimal(str);
        BigDecimal bigDecimal1 = new BigDecimal(str1);
        boolean equals = bigDecimal.equals(bigDecimal1);
        HarePrintUtils.console(equals);

        int i = bigDecimal.compareTo(bigDecimal1);
        HarePrintUtils.console(i);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aa", bigDecimal);
        jsonObject.put("bb", bigDecimal1);

        HarePrintUtils.console(bigDecimal.toString());
        HarePrintUtils.console(bigDecimal1.toString());
        HarePrintUtils.console(JSON.toJSONString(jsonObject));
    }

}
