package my.blog.tag.service;

import lombok.RequiredArgsConstructor;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.tag.tool.ParsingTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Override
    public List<String> saveTags(String tags) {
        List<String> tagList = ParsingTool.parsingTags(tags);
        List<Tag> saveTags = getTagsExceptDuplicateTag(tagList);
        tagRepository.saveAll(saveTags);

        return tagList;
    }

    @Override
    public Long findTagIdByTagName(String tagName) {
        return tagRepository.findTagIdByTagName(tagName);
    }

    private List<Tag> getTagsExceptDuplicateTag(List<String> tagList) {
        List<Tag> saveTags = new ArrayList<>();
        for (String tag : tagList) {
            if (!tagRepository.existsByTagName(tag)) {
                saveTags.add(Tag.of(tag));
            }
        }
        return saveTags;
    }

}
