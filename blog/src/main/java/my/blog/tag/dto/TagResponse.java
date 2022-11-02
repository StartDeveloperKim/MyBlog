package my.blog.tag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagResponse {

    private String tagName;

    public TagResponse(String tagName) {
        this.tagName = tagName;
    }
}
