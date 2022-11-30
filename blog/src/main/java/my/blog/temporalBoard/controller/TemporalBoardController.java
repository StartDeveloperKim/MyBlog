package my.blog.temporalBoard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.temporalBoard.dto.TemporalBoardReq;
import my.blog.temporalBoard.service.TemporalBoardService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/temporal-board")
public class TemporalBoardController {

    private final TemporalBoardService temporalBoardService;

    @PostMapping
    public Long saveTemporalBoard(@Valid @RequestBody TemporalBoardReq temporalBoardReq) {
        log.info("temporalBoardDto {}", temporalBoardReq.toString());
        return temporalBoardService.saveTemporalBoard(temporalBoardReq);
    }

    @PostMapping("/{id}")
    public String updateTemporalBoard(@Valid @RequestBody TemporalBoardReq temporalBoardReq,
                                      @PathVariable("id") Long id) {
        log.info("temporalBoardDto and Id {} {}", temporalBoardReq.toString(), id);

        try {
            temporalBoardService.updateTemporalBoard(temporalBoardReq, id);
            return "success";
        } catch (Exception e) {
            log.error("임시저장 중 오류 발생 {}", e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteTemporalBoard(@PathVariable("id") Long id) {
        try {
            temporalBoardService.deleteTemporalBoard(id);
            return "success";
        } catch (Exception e) {
            log.error("임시저장 글 삭제 중 오류 발생 {}", e.getMessage());
            return "error";
        }
    }
}
