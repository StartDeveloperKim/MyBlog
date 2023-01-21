package my.blog.comments.service;

import my.blog.comments.domain.CommentsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentsServiceImplTest {

    @Mock
    private CommentsRepository commentsRepository;
    @InjectMocks
    private CommentsServiceImpl commentsService;

    @Test
    void 부모댓글을_삭제하면_대댓글까지_삭제된다() {
        //given

        //when
        commentsService.removeComment(1L, 1L);
        //then
        verify(commentsRepository, times(1)).deleteParentComment(anyLong(), anyLong());

    }

}