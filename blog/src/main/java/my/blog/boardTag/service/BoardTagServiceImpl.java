package my.blog.boardTag.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.tag.dto.TagResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardTagServiceImpl implements BoardTagService {

    private final BoardTagRepository boardTagRepository;

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
    public Long getCountBoardByTagName(String tagName) {
        return boardTagRepository.countBoardTagByTagName(tagName);
    }

}
