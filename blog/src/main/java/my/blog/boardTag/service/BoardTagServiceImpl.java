package my.blog.boardTag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.tag.dto.TagResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardTagServiceImpl implements BoardTagService {

    private final BoardTagRepository boardTagRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<BoardResponse> getTagBoardList(int page, int size, String tagName) {
        List<BoardTag> findBoardTags = boardTagRepository.findBoardTagByTagTagName(PageRequest.of(page-1, size), tagName);
        List<Board> boards = findBoardEntitiesById(findBoardTags);

        return boards.stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagResponse> getTagList(Long boardId) {
        return boardTagRepository.findBoardTagsByBoardId(boardId)
                .stream().map(boardTag -> new TagResponse(boardTag.getTag().getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public Long getCountBoardByTagName(String tagName) {
        return boardTagRepository.countBoardTagByTagName(tagName);
    }

    private List<Board> findBoardEntitiesById(List<BoardTag> findBoardTags) {
        List<Board> boards = new ArrayList<>();
        for (BoardTag findBoardTag : findBoardTags) {
            boards.add(boardRepository.findByIdForBoardTag(findBoardTag.getBoard().getId())
                    .orElseThrow(() -> new EntityNotFoundException("엔티티가 없습니다.")));
        }
        return boards;
    }
}
