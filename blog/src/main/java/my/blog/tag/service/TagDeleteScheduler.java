package my.blog.tag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.tag.domain.InMemoryTagRepository;
import my.blog.tag.domain.TagRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class TagDeleteScheduler {

    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    @Scheduled(cron = "0 0 12 * * ?")
    public void autoTagRemove() {
        HashSet<String> tags = InMemoryTagRepository.getInstance();
        List<String> removeTags = new ArrayList<>();

        for (String tag : tags) {
            Long count = boardTagRepository.countBoardTagByTagName(tag);
            if (count == 0) {
                tagRepository.deleteByTagName(tag);
                removeTags.add(tag);
            }
        }

        InMemoryTagRepository.removeTags(removeTags);
    }

}
