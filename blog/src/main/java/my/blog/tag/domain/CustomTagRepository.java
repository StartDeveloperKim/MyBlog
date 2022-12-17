package my.blog.tag.domain;

import java.util.List;

public interface CustomTagRepository {
    List<Tag> findTags(List<String> tags);
}
