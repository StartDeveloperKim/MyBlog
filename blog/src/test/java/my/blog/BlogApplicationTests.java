package my.blog;

import my.blog.board.service.BoardLookupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	private BoardLookupService boardLookupService;

	@Test
	void 게시글리스트_조회하기_성능_테스트() {
		boardLookupService.getBoardList(1, 6, "total", null, "0");
	}

}
