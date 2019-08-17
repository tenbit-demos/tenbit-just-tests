package cn.tenbit.tests.test07;

import cn.tenbit.hare.core.lite.util.HarePrintUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author bangquan.qian
 * @Date 2019-08-16 21:26
 */
public class MyTest {

    private static final AtomicLong startId = new AtomicLong(4179L);

    private static final String dataPath = "/Users/chainz/Downloads/test.txt";

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Category {
        private Long id;
        private String cateName;
        private Long parentId;
        private Integer isLeaf;
        private Integer cateLevel;
        private String cateLevelInfo;
    }

    public static void main(String[] args) throws Exception {
        String data = FileUtils.readFileToString(new File(dataPath));

        Map<String, Category> level0AndLevel1Map = new HashMap<>();
        List<Category> lst = new ArrayList<>();

        String[] lines = data.split("###");
        for (String line : lines) {
            String[] columns = line.split("\\$\\$\\$");
            String level0 = columns[0];
            if (level0AndLevel1Map.get(level0) != null) {
                continue;
            }
            Category category = new Category();
            category.setId(startId.getAndIncrement());
            category.setCateLevel(1);
            category.setParentId(0L);
            category.setCateLevelInfo("0");
            category.setCateName(level0);
            category.setIsLeaf(0);
            lst.add(category);
            level0AndLevel1Map.put(level0, category);
        }

        for (String line : lines) {
            String[] columns = line.split("\\$\\$\\$");
            String level1 = columns[1];
            if (level0AndLevel1Map.get(level1) != null) {
                continue;
            }
            Category category = new Category();
            category.setId(startId.getAndIncrement());
            category.setCateLevel(2);
            Category parentCategory = level0AndLevel1Map.get(columns[0]);
            category.setParentId(parentCategory.getId());
            category.setCateLevelInfo(parentCategory.getCateLevelInfo() + "-" + parentCategory.getId());
            category.setCateName(level1);
            category.setIsLeaf(0);
            lst.add(category);
            level0AndLevel1Map.put(level1, category);
        }

        Map<String, Long> level3Map = new HashMap<>();
        for (String line : lines) {
            String[] columns = line.split("\\$\\$\\$");
            String level2 = null;
            try {
                level2 = columns[2];
            } catch (Exception e) {
                level2 = "其他";
                HarePrintUtils.console(line);
            }
            Category category = new Category();
            category.setId(startId.getAndIncrement());
            category.setCateLevel(3);
            Category parentCategory = level0AndLevel1Map.get(columns[1]);
            category.setParentId(parentCategory.getId());
            category.setCateLevelInfo(parentCategory.getCateLevelInfo() + "-" + parentCategory.getId());
            category.setCateName(level2);
            category.setIsLeaf(1);
            lst.add(category);
            level3Map.put(line, category.getId());
        }

        List<String> excels = new ArrayList<>();
        for (String line : lines) {
            String[] columns = line.split("\\$\\$\\$");
            String excel = columns[0] + "\t" + columns[1] + "\t" + (columns.length < 3 ? "其他" : columns[2]) + "\t" + level3Map.get(line);
            excels.add(excel);
        }

        String sql = "insert into category (id,cate_name,parent_id,is_valid,is_del,is_leaf,cate_level," +
                "create_user_type,create_time,create_user,update_time,update_user,cate_level_info) value " +
                "(#1,'#2',#3,1,1,#4,#5,0,now(),'sys',now(),'sys','#6');";

        List<String> sqls = new ArrayList<>();
        for (Category category : lst) {
            String s = sql.replaceAll("#1", "" + category.getId())
                    .replaceAll("#2", category.getCateName())
                    .replaceAll("#3", "" + category.getParentId())
                    .replaceAll("#4", "" + category.getIsLeaf())
                    .replaceAll("#5", "" + category.getCateLevel())
                    .replaceAll("#6", category.getCateLevelInfo());
            sqls.add(s);
        }

        for (String excel : excels) {
            HarePrintUtils.console(excel);
        }

        HarePrintUtils.newline();

        for (String sqx : sqls) {
            HarePrintUtils.console(sqx);
        }
    }
}
