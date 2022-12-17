package my.blog.board.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.BoardUpdateResponse;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardLookupServiceImpl implements BoardLookupService{

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final BoardTagRepository boardTagRepository;


    @Override
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시물이 없습니다."));
    }

    @Override
    public List<BoardResponse> getBoardList(int page, int size, String parentCategory, String childCategory, String step) {
        PageRequest pageInfo = PageRequest.of(page - 1, size);
        List<Board> findBoards = getBoardsByStep(parentCategory, childCategory, step, pageInfo);

        return findBoards.stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponse> getBoardListRecent() {
        return boardRepository.findTop6ByOrderByCreateDateDesc()
                .stream().map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "totalCount")
    public Long getBoardCount() {
        return boardRepository.count();
    }

    @Override
    public BoardUpdateResponse getBoardUpdateDto(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시물이 없습니다."));
        List<BoardTag> boardTags = boardTagRepository.findBoardTagsByBoardId(boardId);
        return new BoardUpdateResponse(board, boardTags);
    }

    @Override
    public Long getBoardCountByCategory(String parentCategoryName, String childCategoryName) {
        if (parentCategoryName.equals("total")) {
            return this.getBoardCount();
        } else {
            Long categoryId = getCategoryId(parentCategoryName, childCategoryName);
            return boardRepository.getBoardCountByCategoryId(categoryId);
        }
    }

    @Override
    public List<BoardResponse> getBoardSearchResult(String word, PageRequest request) {
        if (!hasText(word)) {
            throw new IllegalArgumentException("검색어가 비어있습니다.");
        }
        List<Board> boards = boardRepository.searchBoardByTitle(word, request);
        return boards.stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long getSearchBoardCount(String searchWord) {
        Long count = boardRepository.searchBoardCount(searchWord);
        return count != null ? count : 0;
    }

    private Long getCategoryId(String parentCategoryName, String childCategoryName) {
        if (hasText(childCategoryName)) {
            return categoryRepository.findByNameAndParentName(parentCategoryName, childCategoryName).getId();
        } else {
            return categoryRepository.findByNameAndParentIdIsNull(parentCategoryName).getId();
        }
    }

    private List<Board> getBoardsByStep(String parentCategory, String childCategory, String step, PageRequest pageInfo) {
        try {
            switch (step) {
                case "0":
                    return boardRepository.findBoardsOrderByIdDesc(pageInfo);
                case "1": {
                    Category findCategory = categoryRepository.findByNameAndParentIdIsNull(parentCategory); // 부모카테고리 찾기
                    return getBoardsByCategoryId(pageInfo, findCategory);
                }
                case "2": {
                    Category findCategory = categoryRepository.findByNameAndParentName(parentCategory, childCategory);
                    return getBoardsByCategoryId(pageInfo, findCategory);
                }
            }
            return new ArrayList<Board>();
        } catch (Exception e) {
            throw new EntityNotFoundException("게시글이 없습니다.");
        }
    }

    private List<Board> getBoardsByCategoryId(PageRequest pageInfo, Category findCategory) {
        return boardRepository.findByCategoryId(findCategory.getId(), pageInfo);
    }
}
