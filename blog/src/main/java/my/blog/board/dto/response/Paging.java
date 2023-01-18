package my.blog.board.dto.response;

import lombok.*;
import org.springframework.cache.annotation.Cacheable;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paging {

    private int startPage;
    private int endPage;

    private int currentPage;
    private int realEndPage;

    private final int displayPageNum = 6; // pagingSize

    public static Paging of(int page, Long totalBoard) {
        Paging paging = new Paging();

        paging.endPage = (int) (Math.ceil(page / (double) paging.displayPageNum)) * paging.displayPageNum;
        paging.startPage = paging.endPage - 5;

        paging.currentPage = page;
        paging.realEndPage = (int) (Math.ceil(totalBoard / (double) paging.displayPageNum));

        if (paging.realEndPage < paging.endPage) {
            paging.endPage = paging.realEndPage;
        }

        return paging;
    }
}
