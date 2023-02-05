package my.blog.heart.service;

public interface HeartService {

    boolean isUserHaveHeart(String email, Long boardId);

    void saveHeart(String email, Long boardId);

    void deleteHeart(String email, Long boardId);

    Long getHeartCount(Long boardId);

}
