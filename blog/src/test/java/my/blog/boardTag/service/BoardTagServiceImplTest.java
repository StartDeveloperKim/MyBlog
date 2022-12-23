package my.blog.boardTag.service;

import my.blog.board.domain.BoardRepository;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static my.blog.factory.EntityFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardTagServiceImplTest {

    @Mock
    private BoardTagRepository boardTagRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private BoardTagServiceImpl boardTagService;

    private User user;
    private Category category;

    private final Long BOARD_ID = 1L;

    @BeforeEach
    void setUp() {
        user = newUserInstance(1L, Role.ADMIN);
        category = newCategoryInstance("카테고리", 1L, null);
    }

    @Test
    void BoardTag엔티티_저장하기() {
        //given
        List<String> tags = List.of("태그1", "태그2");
        List<Tag> findTags = tags.stream()
                .map(t -> newTagInstance(null, t))
                .collect(Collectors.toList());
        when(boardRepository.findById(BOARD_ID))
                .thenReturn(Optional.of(newBoardInstance(user, category, BOARD_ID)));
        when(tagRepository.findTags(tags))
                .thenReturn(findTags);
        //when
        boardTagService.saveBoardTags(tags, BOARD_ID);
        //then
        assertAll(
                () -> verify(boardRepository, times(1)).findById(anyLong()),
                () -> verify(tagRepository, times(1)).findTags(tags),
                () -> verify(boardTagRepository, times(1)).saveAll(any())
        );
    }
}