package my.blog.temporalBoard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.tag.tool.ParsingTool;
import my.blog.temporalBoard.domain.TemporalBoard;
import my.blog.temporalBoard.domain.TemporalBoardRepository;
import my.blog.temporalBoard.dto.TemporalBoardReq;
import my.blog.temporalBoard.dto.TemporalBoardResp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TemporalBoardServiceImpl implements TemporalBoardService{

    private final TemporalBoardRepository temporalBoardRepository;

    @Override
    @Transactional(readOnly = true)
    public TemporalBoardResp getTemporalBoard(Long id) {
        TemporalBoard temporalBoard = getTemporalBoardById(id);
        return getTemporalBoardResp(temporalBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public TemporalBoardResp getRecentTemporalBoard() {
        TemporalBoard findTemporalBoard = temporalBoardRepository.findTop1ByOrderByIdDesc();
        if (findTemporalBoard == null) {
            return null;
        }else {
            return getTemporalBoardResp(findTemporalBoard);
        }
    }

    @Override
    public Long saveTemporalBoard(TemporalBoardReq temporalBoardReq) {
        TemporalBoard temporalBoard = TemporalBoard.newInstance(temporalBoardReq);
        return temporalBoardRepository.save(temporalBoard).getId();
    }

    @Override
    public void updateTemporalBoard(TemporalBoardReq temporalBoardReq, Long id) {
        TemporalBoard findTemporalBoard = getTemporalBoardById(id);
        findTemporalBoard.update(temporalBoardReq);
    }

    @Override
    public void deleteTemporalBoard(Long id) {
        temporalBoardRepository.delete(getTemporalBoardById(id));
    }

    private TemporalBoard getTemporalBoardById(Long id) {
        return temporalBoardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("임시저장 글이 없습니다."));
    }

    private static TemporalBoardResp getTemporalBoardResp(TemporalBoard temporalBoard) {
        List<String> tags = ParsingTool.parsingTags(temporalBoard.getTags());
        return new TemporalBoardResp(temporalBoard.getId(), temporalBoard.getTitle(),
                temporalBoard.getContent(), temporalBoard.getThumbnail(),
                temporalBoard.getCategoryId(), temporalBoard.getCreateDate(), tags);
    }
}
