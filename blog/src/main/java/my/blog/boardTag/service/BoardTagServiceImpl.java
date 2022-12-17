package my.blog.boardTag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.tag.dto.TagCountResponse;
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
    private final TagRepository tagRepository;

    @Override
    public List<BoardResponse> getTagBoardList(int page, int size, String tagName) {
        List<BoardTag> findBoardTags = boardTagRepository.findBoardTagByTagTagName(PageRequest.of(page-1, size), tagName);

        return findBoardTags.stream()
                .map(boardTag -> new BoardResponse(boardTag.getBoard()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagResponse> getTagList(Long boardId) {
        return boardTagRepository.findBoardTagsByBoardId(boardId)
                .stream().map(boardTag -> new TagResponse(boardTag.getTag().getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagCountResponse> getPopularityTag() {
        Long LIMIT = 20L;
        return boardTagRepository.findTagCountDtoOrderByCount(LIMIT);
    }

    @Override
    @Transactional
    public void saveBoardTags(List<String> tags, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
        List<Tag> findTags = tagRepository.findTags(tags);

        List<BoardTag> boardTags = findTags.stream()
                .map(tag -> BoardTag.newInstance(board, tag))
                .collect(Collectors.toList());

        boardTagRepository.saveAll(boardTags);
    }

    @Override
    @Transactional
    public void editBoardTags(List<String> tags, Long boardId) {
        boardTagRepository.deleteByBoardId(boardId);
        saveBoardTags(tags, boardId);
    } // 이 코드도 뭔가 애매하다...

    @Override
    public Long getCountBoardByTagName(String tagName) {
        return boardTagRepository.countBoardTagByTagName(tagName);
    }

}
