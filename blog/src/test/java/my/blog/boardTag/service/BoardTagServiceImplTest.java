package my.blog.boardTag.service;

import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardTagServiceImplTest {

    @Autowired
    BoardTagService boardTagService;

    @Test
    @DisplayName("태그이름별 게시판 가져오기 테스트")
    void getBoardTagTest() {
        String tagName = "이미지";

        List<BoardResponse> tagBoardList = boardTagService.getTagBoardList(1, 6, tagName);

        for (BoardResponse findBoardTag : tagBoardList) {
            System.out.println("find" + findBoardTag.getTitle());
        }
    }
}