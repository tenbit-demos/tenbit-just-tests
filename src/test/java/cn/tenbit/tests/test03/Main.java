package cn.tenbit.tests.test03;

import cn.tenbit.hare.core.lite.util.HareJsonUtils;
import cn.tenbit.hare.core.lite.util.HarePrintUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangquan.qian
 * @Date 2019-07-04 11:30
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFileToString(new File("/Users/chainz/Downloads/activitySupplyPrice.json"));
        input = input.replaceAll("\n","%");
        List<String> data = new ArrayList<>();
        List<String> sqls = new ArrayList<>();
        String[] splits = input.split("%");
        for (String split : splits) {
            String[] ss = split.split("#");
            String activityId = ss[0];
            String activityName = ss[1];
            String goodsId = ss[2];
            JSONArray jsonArray = HareJsonUtils.parseArray(ss[3]);
            for (Object obj : jsonArray) {
                String string = HareJsonUtils.toJsonString(obj);
                JSONObject jsonObject = HareJsonUtils.parseObject(string);
                String skuId = jsonObject.getString("skuId");
                String activitySupplyPrice = jsonObject.getString("activitySupplyPrice");
                String activityPrice = jsonObject.getString("price");
                String join = StringUtils.joinWith("\t", activityId, activityName, goodsId, skuId, activityPrice, activitySupplyPrice);
                String sql = String.format("update sku_info set activity_supply_price = %s where sku_id = %s;", activitySupplyPrice, skuId);
                sqls.add(sql);
                data.add(join);
            }
        }

//        System.out.println(StringUtils.joinWith("\t","活动ID","活动名称","商品ID","活动售价","活动供货价"));
//        for (String str : data) {
//            System.out.println(str);
//        }

//        System.out.println();
        StringBuilder sb = new StringBuilder();
        for (String str : sqls) {
            sb.append(str).append("\n");
        }

        HarePrintUtils.stringFile("/Users/chainz/Downloads/activitySupplyPrice_out.json", sb.toString());
    }
}
