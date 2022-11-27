package my.blog.temporalBoard.service;

import my.blog.temporalBoard.dto.TemporalBoardReq;
import my.blog.temporalBoard.dto.TemporalBoardResp;

public interface TemporalBoardService {

    // 저장, 삭제, 업데이트
    // 저장 -> 임시저장
    // 매 1분마다 업데이트 쿼리 나감
    // 저장 버튼 누르면 삭제된다.
    TemporalBoardResp getTemporalBoard(Long id);

    TemporalBoardResp getRecentTemporalBoard();

    Long saveTemporalBoard(TemporalBoardReq temporalBoardReq);

    void updateTemporalBoard(TemporalBoardReq temporalBoardReq, Long id);

    void deleteTemporalBoard(Long id);
}
