package my.blog.comments.service;

import my.blog.comments.dto.CommentEditRequest;
import my.blog.comments.dto.CommentRequest;
import my.blog.comments.dto.CommentResponse;

import java.util.List;

public interface CommentsService {

    /*
     * 리팩토링 할 때 DTO를 직접넘기지 말고 Java MapStruct 라이브러리를 사용해보자
     * 1. 댓글 등록
     * 2. 댓글 삭제
     * 3. 댓글 수정
     * 4. 댓글 가져오기
     * */
    List<CommentResponse> getComments(Long boardId);

    void saveComment(String content, Long boardId, Long userId);

    void updateComment(CommentEditRequest editRequest);

    void removeComment(CommentEditRequest editRequest);
}
