package my.blog.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.comments.domain.Comments;
import my.blog.comments.domain.CommentsRepository;
import my.blog.comments.dto.HierarchicalComment;
import my.blog.comments.dto.request.CommentEditRequest;
import my.blog.comments.dto.request.CommentRequest;
import my.blog.comments.dto.response.CommentResponse;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<Long, CommentResponse> getComments(Long boardId, String email) {
        List<Comments> comments = commentsRepository.findCommentsByBoardId(boardId);

        return HierarchicalComment.createHierarchicalComment(comments, email);
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalComment(Long boardId) {
        return commentsRepository.getCommentsCountByBoardId(boardId);
    }

    @Override
    public void saveComment(CommentRequest commentRequest, Long boardId, String email) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("멤버가 없습니다."));
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시판이 없습니다."));

        Comments comment = Comments.newInstance(findBoard, findUser, commentRequest.getComment(), commentRequest.getParentId());

        commentsRepository.save(comment);
    }

    @Override
    public void updateComment(CommentEditRequest editRequest) {
        Comments findComment = commentsRepository.findById(editRequest.getCommentId()).orElseThrow(() -> new EntityNotFoundException("댓글이 없습니다."));
        findComment.editComment(editRequest.getComment());
    }

    @Override
    public void removeComment(Long commentId, Long boardId) {
        commentsRepository.deleteParentComment(commentId, boardId);
    }
}
