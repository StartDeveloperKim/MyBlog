package my.blog.comments.service;

import my.blog.board.domain.BoardRepository;
import my.blog.comments.domain.CommentsRepository;
import my.blog.comments.dto.request.CommentDeleteRequest;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentsServiceImplTest {

    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private CommentsServiceImpl commentsService;

    @Test
    void 부모댓글을_삭제하면_대댓글까지_삭제된다() {
        //given
        CommentDeleteRequest commentDeleteRequest = new CommentDeleteRequest();
        ReflectionTestUtils.setField(commentDeleteRequest, "parentComment", true);
        ReflectionTestUtils.setField(commentDeleteRequest, "boardId", 1L);
        ReflectionTestUtils.setField(commentDeleteRequest, "commentId", 1L);

        //when
        commentsService.removeComment(commentDeleteRequest);
        //then
        verify(commentsRepository, times(1)).deleteParentComment(anyLong(), anyLong());

    }

}