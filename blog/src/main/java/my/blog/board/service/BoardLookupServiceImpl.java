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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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

        if (findBoards == null) {
            throw new EntityNotFoundException("게시글이 없습니다.");
        }

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
    public Long getBoardCount() {
        return boardRepository.getAllBoardCount();
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
            return boardRepository.getAllBoardCount();
        } else {
            Category findCategory;
            if (childCategoryName.equals("")) {
                findCategory = categoryRepository.findByNameAndParentIdIsNull(parentCategoryName);
            } else {
                findCategory = categoryRepository.findByNameAndParentName(parentCategoryName, childCategoryName);
            }
            return boardRepository.getBoardCountByCategoryId(findCategory.getId());
        }
    }

    private List<Board> getBoardsByStep(String parentCategory, String childCategory, String step, PageRequest pageInfo) {
        List<Board> findBoards = null;
        switch (step) {
            case "0":
                findBoards = boardRepository.findByOrderByIdDesc(pageInfo).getContent();
                break;
            case "1": {
                Category findCategory = categoryRepository.findByNameAndParentIdIsNull(parentCategory); // 부모카테고리 찾기
                findBoards = getBoardsByCategoryId(pageInfo, findCategory);
                break;
            }
            case "2": {
                Category findCategory = categoryRepository.findByNameAndParentName(parentCategory, childCategory);
                findBoards = getBoardsByCategoryId(pageInfo, findCategory);
                break;
            }
        }
        return findBoards;
    }

    private List<Board> getBoardsByCategoryId(PageRequest pageInfo, Category findCategory) {
        return boardRepository.findByCategoryId(findCategory.getId(), pageInfo).getContent();
    }
}
