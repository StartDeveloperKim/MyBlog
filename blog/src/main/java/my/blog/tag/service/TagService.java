package my.blog.tag.service;

import java.util.List;

public interface TagService {

    List<String> saveTags(List<String> tags);

    List<String> getTags();

}
