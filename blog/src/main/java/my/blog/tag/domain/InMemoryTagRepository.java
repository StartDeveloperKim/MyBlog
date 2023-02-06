package my.blog.tag.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InMemoryTagRepository {

    private static final HashSet<String> inMemoryTagRepository = new HashSet<>();

    private InMemoryTagRepository() {}

    public static boolean isDuplicateTag(String tag) {
        return !inMemoryTagRepository.contains(tag); // 중복x -> True
    }

    public static void clear() {
        inMemoryTagRepository.clear();
    }

    public static void addTag(String tag) {
        inMemoryTagRepository.add(tag);
    }

    public static void addTags(Set<String> tags) {
        inMemoryTagRepository.addAll(tags);
    }

    public static HashSet<String> getInstance() {
        return inMemoryTagRepository;
    }

    public static void removeTags(List<String> tags) {
        for (String tag : tags) {
            inMemoryTagRepository.remove(tag);
        }
    }
}
