package my.blog.tag.service;

import lombok.RequiredArgsConstructor;
import my.blog.tag.domain.InMemoryTagRepository;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
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
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<String> saveTags(List<String> tags) {
        if (tags.size() == 0) {
            return new ArrayList<>();
        }
        //List<String> tagList = ParsingTool.parsingTags(tags);
        Set<Tag> tagSet = getTagsExceptDuplicateTagAtMemory(tags);
        tagRepository.saveAll(tagSet);

        return tags;
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

    @Override
    public List<String> getTags() {
        return tagRepository.findTop30ByOrderByIdDesc()
                .stream().map(Tag::getTagName)
                .collect(Collectors.toList());
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
