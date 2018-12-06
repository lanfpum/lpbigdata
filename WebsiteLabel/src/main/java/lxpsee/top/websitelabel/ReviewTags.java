package lxpsee.top.websitelabel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/5 15:58.
 * <p>
 * 标签工具类（查看标签）
 * 包含提取标签的方法
 */
public class ReviewTags {
    public static String extractTags(String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        if (jsonObject == null || !jsonObject.containsKey("extInfoList")) {
            return "";
        }

        JSONArray extInfoList = jsonObject.getJSONArray("extInfoList");

        if (extInfoList == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        int size = extInfoList.size();

        for (int i = 0; i < size; i++) {
            JSONObject object = extInfoList.getJSONObject(i);

            if (object != null && object.containsKey("title") && object.getString("title").
                    equals("contentTags") && object.containsKey("values")) {
                JSONArray values = object.getJSONArray("values");

                if (values == null) {
                    continue;
                }

                boolean begin = true;
                int valueSize = values.size();

                for (int j = 0; j < valueSize; j++) {
                    if (begin) {
                        begin = false;
                    } else {
                        stringBuilder.append(",");
                    }

                    stringBuilder.append(values.getString(j));
                }
            }
        }

        return stringBuilder.toString();

    }

    public static void main(String[] args) {
        String s = "{\"reviewPics\":[{\"picId\":2405538806,\"url\":\"http://p0.where.net/shaitu/7c10019c62947d01ded80cc698c77c90217708.jpg\",\"status\":1},{\"picId\":2405442602,\"url\":\"http://p0.meituan.net/shaitu/d41ef06f5d16d5d3cbc871765ff93130270451.jpg\",\"status\":1}],\"extInfoList\":[{\"title\":\"contentTags\",\"values\":[\"回头客\",\"上菜快\",\"环境优雅\",\"性价比高\",\"菜品不错\"],\"desc\":\"\",\"defineType\":0},{\"title\":\"tagIds\",\"values\":[\"493\",\"232\",\"24\",\"300\",\"1\"],\"desc\":\"\",\"defineType\":0}],\"expenseList\":null,\"reviewIndexes\":[1,2],\"scoreList\":null}";
        System.out.println(extractTags(s));
        System.out.println(extractTags(""));
        System.out.println(extractTags(null));
    }
}
