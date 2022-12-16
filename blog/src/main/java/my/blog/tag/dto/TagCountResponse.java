package my.blog.tag.dto;

import lombok.Getter;

@Getter
public class TagCountResponse {

    private final String name;
    private final Long count;

    public TagCountResponse(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
