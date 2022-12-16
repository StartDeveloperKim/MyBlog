package my.blog.boardTag.domain;

import my.blog.tag.dto.TagCountResponse;

import java.util.List;

public interface CustomBoardTagRepository {
    List<TagCountResponse> findTagCountDtoOrderByCount(Long limit);


}
