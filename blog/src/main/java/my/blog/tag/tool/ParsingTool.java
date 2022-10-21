package my.blog.tag.tool;

import com.google.gson.Gson;

public class ParsingTool {
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static Gson getGson() {
        return gson;
    }
}
