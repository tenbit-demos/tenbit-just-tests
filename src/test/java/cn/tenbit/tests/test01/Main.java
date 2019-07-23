package cn.tenbit.tests.test01;

import cn.tenbit.hare.core.lite.util.HareJsonUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author bangquan.qian
 * @Date 2019-07-01 14:53
 */
public class Main {

    public static final String input = "[\"440000\",\"441900\",null,\"xx\"]\n" +
            "[\"440000\",\"440500\",\"440513\",\"yy\"]\n" +
            "[\"440000\",\"440300\",\"440307\",\"zz\"]\n" +
            "[\"440000\",\"440300\",\"440304\",\"aa\"]\n" +
            "[\"440000\",\"440100\",\"440111\",\"bb-22 \"]\n" +
            "[\"440000\",\"440100\",\"440111\",\"vv-33 \"]\n" +
            "[\"330000\",\"330600\",\"330683\",\"dd\"]\n" +
            "[\"330000\",\"330600\",\"330683\",\"ee\"]\n" +
            "[\"330000\",\"330100\",\"330110\",\"ff\"]\n" +
            "[\"330000\",\"330100\",\"330104\",\"gg\"]\n" +
            "[\"320000\",\"321000\",\"321088\",\"dd\"]\n" +
            "[\"320000\",\"321000\",\"321088\",\"ff\"]\n" +
            "[\"310000\",\"310100\",\"310118\",\"ss\"]\n" +
            "[\"310000\",\"310100\",\"310117\",\"ee\"]\n" +
            "[\"310000\",\"310100\",\"310115\",\"ss\"]\n" +
            "[\"310000\",\"310100\",\"310110\",\"vv\"]\n" +
            "[\"140000\",\"140300\",\"140302\",\"ss\"]\n" +
            "[\"130000\",\"130600\",\"130681\",\"gg\"]\n" +
            "[\"110000\",\"110100\",\"110101\",\"ss（ff）ss-11\"]\n";

    public static void main(String[] args) throws Exception {
        String json = FileUtils.readFileToString(new File("/Users/chainz/Temporary/location.json"));
        // HarePrintUtils.prettyJsonConsole(json);

        JSONObject jsonObject = HareJsonUtils.parseObject(json);
        Collection<Object> values = jsonObject.values();
        // HarePrintUtils.prettyJsonConsole(values);

        JSONObject njo = new JSONObject();
        for (Object obj : values) {
            if (obj instanceof JSONObject) {
                JSONObject jo = (JSONObject) obj;
                njo.putAll(jo);
            }
        }
        //HarePrintUtils.prettyJsonConsole(njo);

        String inp = input.replaceAll("\n", ";");
        // HarePrintUtils.prettyJsonConsole(inp);
        List<List<String>> out = new ArrayList<>();
        String[] split = inp.split(";");
        for (String str : split) {
            str = str.replaceAll("null","\"无\"");
            List<String> strs = HareJsonUtils.parseArray(str, String.class);
            List<String> outs = new ArrayList<>();
            for(String s:strs){
                if(!StringUtils.isNumeric(s)){
                    outs.add(s);
                    continue;
                }
                String string = njo.getString(s);
                outs.add(string);
            }
            out.add(outs);
        }

        // HarePrintUtils.prettyJsonConsole(out);

        for(List<String> list:out){
            for(String str:list){
                System.out.print(str+"\t");
            }
            System.out.println();
        }
    }
}
