package my.blog.heart.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.heart.domain.Heart;
import my.blog.heart.domain.HeartRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public void saveHeart(String email, Long boardId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 없습니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        heartRepository.save(Heart.newInstance(user, board));
    }

    @Override
    public void deleteHeart(String email, Long boardId) {
        heartRepository.removeHeart(boardId, email);
    }

    @Override
    public Long getHeartCount(Long boardId) {
        return heartRepository.countByBoard_Id(boardId);
    }

    @Override
    public boolean isUserLikeBoard(Long boardId, Long userId) {
        return heartRepository.existHeart(boardId, userId);
    }
}
