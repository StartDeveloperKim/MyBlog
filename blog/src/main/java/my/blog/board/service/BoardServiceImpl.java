package my.blog.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.Paging;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.tag.dto.TagResponse;
import my.blog.tag.tool.ParsingTool;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    @Override
    public Long writeBoardWithTag(BoardRegister boardRegister, List<String> tags, Long userId) {
        List<BoardTag> boardTags = new ArrayList<>();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("멤버가 없습니다."));
        /*Category category = categoryRepository.findByCategoryName(boardRegister.getCategory());*/
        Category category = categoryRepository.findById(boardRegister.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("카테고리가 없습니다."));

        Board board = Board.of(user, category, boardRegister);
        Board saveBoard = boardRepository.save(board); // 게시글 저장

        for (String tag : tags) {
            Tag findTag = tagRepository.findByTagName(tag);
            boardTags.add(BoardTag.from(saveBoard, findTag));
        }
        boardTagRepository.saveAll(boardTags);

        return saveBoard.getId();
    }

    @Override
    public Long writeBoard(BoardRegister boardRegister, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("멤버가 없습니다."));
        /*Category category = categoryRepository.findByCategoryName(boardRegister.getCategory());*/
        Category category = categoryRepository.findById(boardRegister.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("카테고리가 없습니다."));

        Board board = Board.of(user, category, boardRegister);
        Board saveBoard = boardRepository.save(board); // 게시글 저장

        return saveBoard.getId();
    }

    @Override
    public void editBoard(BoardUpdate boardUpdate) {
        Board board = boardRepository.findById(boardUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
        Category category = categoryRepository.findByCategoryName(boardUpdate.getCategory());

        board.edit(boardUpdate.getTitle(), boardUpdate.getContent(), boardUpdate.getThumbnail(), category);
    }

    @Override
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public void addHit(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
        board.addHit();
    }

    @Override
    @Transactional(readOnly = true)
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시물이 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponse> getBoardList(int page, int size, String parentCategory, String childCategory,String step) {
        PageRequest pageInfo = PageRequest.of(page - 1, size);

        List<Board> findBoards = null;
        if (step.equals("0")) {
            findBoards = boardRepository.findByOrderByIdDesc(pageInfo).getContent();
        } else if (step.equals("1")) {
            Category findCategory = categoryRepository.findByNameAndParentIdIsNull(parentCategory); // 부모카테고리 찾기
            findBoards = boardRepository.findByCategoryId(findCategory.getId(), pageInfo).getContent();
        } else if (step.equals("2")) {
            Category findCategory = categoryRepository.findByNameAndParentName(parentCategory, childCategory);
            findBoards = boardRepository.findByCategoryId(findCategory.getId(), pageInfo).getContent();
        }

        if (findBoards == null) {
            throw new EntityNotFoundException("게시글이 없습니다.");
        }

        return findBoards.stream()
                .map(BoardResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponse> getBoardListRecent() {
        return boardRepository.findTop6ByOrderByCreateDateDesc()
                .stream().map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBoardCount() {
        return boardRepository.getAllBoardCount();
    }

    @Override
    @Transactional(readOnly = true)
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
}
