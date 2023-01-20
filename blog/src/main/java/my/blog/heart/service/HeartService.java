package my.blog.heart.service;

public interface HeartService {

    void saveHeart(String email, Long boardId);

    void deleteHeart(String email, Long boardId);

    Long getHeartCount(Long boardId);

    boolean isUserLikeBoard(Long boardId, Long userId);
}
