package my.blog.tag.tool;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ParsingToolTest {

    @Test
    void JSON형식의_문자열을_Map형태로_변환_후_Tag리스트를_파싱한다() {
        //given
        String tags = "[{\"value\":\"테스트1\"},{\"value\":\"테스트2\"},{\"value\":\"테스트3\"}]";
        //when
        List<String> result = ParsingTool.parsingTags(tags);
        //then
        checkTagContainInResult(result, "테스트1");
        checkTagContainInResult(result, "테스트2");
        checkTagContainInResult(result, "테스트3");

    }

    private static void checkTagContainInResult(List<String> strings, String tag) {
        assertThat(strings.contains(tag)).isTrue();
    }
}