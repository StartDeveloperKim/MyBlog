package my.blog.heart.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.heart.domain.Heart;
import my.blog.heart.domain.HeartRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public void saveHeart(Long userId, Long boardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 없습니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        Heart heart = Heart.of(user, board);
        heartRepository.save(heart);
    }

    @Override
    public void deleteHeart(Long userId, Long boardId) {
        heartRepository.removeHeart(userId, boardId);
    }

    @Override
    public Long getHeartCount(Long boardId) {
        return heartRepository.countByBoard_Id(boardId);
    }
}
