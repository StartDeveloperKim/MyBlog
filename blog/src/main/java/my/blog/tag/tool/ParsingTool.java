package my.blog.tag.tool;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParsingTool {
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static List<String> parsingTags(String tags) {
        List<Map<String, String>> parsingTags = gson.fromJson(tags, ArrayList.class);
        List<String> tagList = new ArrayList<>();
        for (Map<String, String> tag : parsingTags) {
            tagList.add(tag.get("value"));
        }
        return tagList;
    }
}
