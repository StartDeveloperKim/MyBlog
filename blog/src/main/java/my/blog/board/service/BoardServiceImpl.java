package my.blog.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.tag.domain.InMemoryTagRepository;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @CacheEvict(value = {"CategoryLayoutStore", "totalCount"}, allEntries = true)
    @Override
    public Long writeBoard(BoardRegister boardRegister, Long userId) {
        User user = getUserEntity(userId);
        Category category = getCategoryEntity(boardRegister.getCategoryId());

        Board board = Board.newInstance(user, category, boardRegister.getTitle(), boardRegister.getContent(), boardRegister.getThumbnail());
        Board saveBoard = boardRepository.save(board); // 게시글 저장

        return saveBoard.getId();
    }

    @CacheEvict(value = "CategoryLayoutStore", allEntries = true)
    @Override
    public void editBoard(BoardUpdate boardUpdate, Long boardId) {
        Board board = getBoardEntity(boardId);
        Category category = getCategoryEntity(boardUpdate.getCategoryId());

        board.edit(boardUpdate.getTitle(), boardUpdate.getContent(), boardUpdate.getThumbnail(), category);
    }

    @CacheEvict(value = {"CategoryLayoutStore", "totalCount"}, allEntries = true)
    @Override
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public void addHit(Long boardId) {
        getBoardEntity(boardId).addHit();
    }

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("멤버가 없습니다."));
    }

    private Category getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("카테고리가 없습니다."));
    }

    private Board getBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
    }
}
