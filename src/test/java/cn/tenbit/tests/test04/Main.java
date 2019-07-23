package cn.tenbit.tests.test04;

import cn.tenbit.hare.core.lite.util.HareJsonUtils;
import cn.tenbit.hare.core.lite.util.HarePrintUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangquan.qian
 * @Date 2019-07-04 14:34
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String s = FileUtils.readFileToString(new File("/Users/chainz/Downloads/goodsId.json"));
        s = s.replaceAll("\n",",");
        s = "[" + s + "]";
        List<Long> lst = HareJsonUtils.parseArray(s, Long.class);

        int eachSize = 200;
        int totalSize = lst.size();
        int times = (int) Math.ceil((double) totalSize / (double) eachSize);

        List<List<Long>> splits = new ArrayList<>();
        for (int idx = 0; idx < times; idx++) {
            List<Long> splitList = lst.subList(idx * eachSize, Math.min(totalSize, (idx + 1) * eachSize));
            if (CollectionUtils.isEmpty(splitList)) {
                continue;
            }
            splits.add(splitList);
        }

        StringBuilder sb = new StringBuilder();
        for (List<Long> split : splits) {
            sb.append(StringUtils.join(split, ",")).append("\n\n");
        }

        HarePrintUtils.stringFile("/Users/chainz/Downloads/goodsId_out.json",sb.toString());
    }
}
