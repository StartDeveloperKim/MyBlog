package my.blog.tag.service;

import lombok.RequiredArgsConstructor;
import my.blog.tag.domain.InMemoryTagRepository;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.tag.tool.ParsingTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Override
    public List<String> saveTags(String tags) {
        if (tags.equals("")) {
            return null;
        }
        List<String> tagList = ParsingTool.parsingTags(tags);
        Set<Tag> tagSet = getTagsExceptDuplicateTagAtMemory(tagList);
        tagRepository.saveAll(tagSet);

        return tagList;
    }

    @Override
    public Long findTagIdByTagName(String tagName) {
        return tagRepository.findTagIdByTagName(tagName);
    }


    private Set<Tag> getTagsExceptDuplicateTagAtMemory(List<String> tags) {
        Set<Tag> result = new HashSet<>();
        for (String tag : tags) {
            if (InMemoryTagRepository.isDuplicateTag(tag)) {
                InMemoryTagRepository.addTag(tag);
                result.add(Tag.newInstance(tag));
            }
        }
        return result;
    }

    @PostConstruct
    void InMemoryTagRepositorySetUp() {
        Set<String> collect = tagRepository.findAll()
                .stream()
                .map(Tag::getTagName)
                .collect(Collectors.toSet());
        InMemoryTagRepository.addTags(collect);
    }


}
