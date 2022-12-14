package my.blog.comments.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.blog.comments.domain.Comments;
import my.blog.comments.dto.response.ChildCommentDto;
import my.blog.comments.dto.response.CommentResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HierarchicalComment {

    public static Map<Long, CommentResponse> createHierarchicalComment(List<Comments> commentsList) {
        Map<Long, CommentResponse> result =new HashMap<>();

        for (Comments comment : commentsList) {
            if (comment.getParentId() == null) {
                result.put(comment.getId(), new CommentResponse(comment));
            } else {
                result.get(comment.getParentId()).getChildCommentDtos().add(new ChildCommentDto(comment));
            }
        }

        return result;
    }
}
