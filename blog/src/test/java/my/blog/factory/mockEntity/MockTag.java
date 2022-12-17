package my.blog.factory.mockEntity;

import lombok.Getter;
import my.blog.boardTag.domain.BoardTag;
import my.blog.tag.domain.Tag;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MockTag extends Tag {

    private Long id;
    private String tagName;
    private List<BoardTag> boardTags = new ArrayList<>();

    public MockTag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
