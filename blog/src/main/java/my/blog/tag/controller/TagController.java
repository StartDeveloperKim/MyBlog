package my.blog.tag.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.Paging;
import my.blog.boardTag.service.BoardTagService;
import my.blog.web.layout.LayoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/board/tag")
public class TagController {

    private final BoardTagService boardTagService;
    private final LayoutService layoutService;

    @GetMapping
    public String getBoardTagList(@RequestParam("tagName") String tagName,
                                  @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                  Model model) {
        int pageSize = 6;
        List<BoardResponse> tagBoardList = boardTagService.getTagBoardList(page, pageSize, tagName);
        Paging pagingInfo = Paging.of(page, boardTagService.getCountBoardByTagName(tagName));

        layoutService.getLayoutInfo(model);

        model.addAttribute("boardList", tagBoardList);
        model.addAttribute("pagingInfo", pagingInfo);

        return "board/boardListForm";
    }
}
