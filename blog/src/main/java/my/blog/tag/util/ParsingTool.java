package my.blog.tag.util;

import java.util.Arrays;
import java.util.List;

public class ParsingTool {
    public static List<String> parsingTags(String tags) {
        int length = tags.length();
        String substring = tags.substring(1, length - 1);

        return Arrays.asList(substring.split(","));
    }
}