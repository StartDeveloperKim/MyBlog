package my.blog.comments.dto;

import my.blog.comments.domain.Comments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
