package my.blog.board.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardResponse;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
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
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Long writeBoard(BoardRegister boardRegister) {
        User user = userRepository.findById(boardRegister.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("멤버가 없습니다."));

        Category category = categoryRepository.findByCategoryName(boardRegister.getCategory());
        Board board = Board.of(user, category, boardRegister);
        /*
        * 1. 태그저장
        * 2. 게시글 저장
        * 3. BoardTag 생성 후 저장
        * */
        Board save = boardRepository.save(board);
        return save.getId();
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
    public List<BoardResponse> getBoardList() {
        List<Board> all = boardRepository.findAll();

        return all.stream().map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponse> getBoardListRecent() {
        return boardRepository.findTop4ByOrderByCreateDateDesc().stream().map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBoardCount() {
        return boardRepository.getAllBoardCount();
    }
}
