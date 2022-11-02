package my.blog.board.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Paging {

    private int startPage;
    private int endPage;

    private int currentPage;
    private int realEndPage;

    private boolean isPreviousPage;
    private boolean isNextPage;

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

        paging.isPreviousPage = paging.currentPage > 1;
        paging.isNextPage = paging.currentPage < paging.realEndPage;


        return paging;
    }
}
