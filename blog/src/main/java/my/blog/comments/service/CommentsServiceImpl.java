package my.blog.comments.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.response.BoardResponse;
import my.blog.comments.domain.Comments;
import my.blog.comments.domain.CommentsRepository;
import my.blog.comments.dto.CommentEditRequest;
import my.blog.comments.dto.CommentRequest;
import my.blog.comments.dto.CommentResponse;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long boardId) {
        return commentsRepository.findCommentsByBoardId(boardId)
                .stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    @Override
    public int getTotalComment(Long boardId) {
        return commentsRepository.getCommentsCountByBoardId(boardId);
    }

    @Override
    public void saveComment(String content, Long boardId, Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("멤버가 없습니다."));
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("게시판이 없습니다."));

        Comments comment = Comments.of(findBoard, findUser, content);

        commentsRepository.save(comment);
    }

    @Override
    public void updateComment(CommentEditRequest editRequest) {
        Comments findComment = commentsRepository.findById(editRequest.getCommentId()).orElseThrow(() -> new EntityNotFoundException("댓글이 없습니다."));
        findComment.editComment(editRequest.getComment());
    }

    @Override
    public void removeComment(CommentEditRequest editRequest) {

    }
}
