package my.blog.board.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

        if (boardRegister.getCategory() == null) {
            boardRegister.setCategory("total");
        }

        Category category = categoryRepository.findByCategoryName(boardRegister.getCategory());
        Board board = Board.of(user, category, boardRegister);
        //아직 태그에 대한 기능을 넣지 않았다. 좀 더 고민해보자.
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
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }
}
