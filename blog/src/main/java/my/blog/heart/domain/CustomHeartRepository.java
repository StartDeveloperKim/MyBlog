package my.blog.heart.domain;

public interface CustomHeartRepository {

    boolean existHeart(Long boardId, Long userId);
}
