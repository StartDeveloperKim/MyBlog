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
        List<String> tagList = ParsingTool.parsingTags(tags);
        /*List<Tag> saveTags = getTagsExceptDuplicateTagAtDB(tagList);
        tagRepository.saveAll(saveTags);*/
        Set<Tag> tagSet = getTagsExceptDuplicateTagAtMemory(tagList);
        tagRepository.saveAll(tagSet);

        return tagList;
    }

    @Override
    public Long findTagIdByTagName(String tagName) {
        return tagRepository.findTagIdByTagName(tagName);
    }

    private List<Tag> getTagsExceptDuplicateTagAtDB(List<String> tagList) {
        List<Tag> saveTags = new ArrayList<>();
        for (String tag : tagList) {
            if (!tagRepository.existsByTagName(tag)) {
                saveTags.add(Tag.of(tag));
            }
        }
        return saveTags;
    }

    private Set<Tag> getTagsExceptDuplicateTagAtMemory(List<String> tags) {
        return tags.stream()
                .filter(InMemoryTagRepository::isDuplicateTag)
                .map(Tag::of)
                .collect(Collectors.toSet());
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
