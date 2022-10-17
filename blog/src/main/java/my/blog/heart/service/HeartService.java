package my.blog.heart.service;

public interface HeartService {

    void saveHeart(Long userId, Long boardId);

    void deleteHeart(Long userId, Long boardId);

    Long getHeartCount(Long boardId);
}
