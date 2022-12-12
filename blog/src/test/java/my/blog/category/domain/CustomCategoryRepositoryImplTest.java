package my.blog.category.domain;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import my.blog.board.domain.QBoard;
import my.blog.category.dto.response.CategoryInfoDto;
import org.hibernate.sql.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomCategoryRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory query;

    @BeforeEach
    void setUp() {
        query = new JPAQueryFactory(entityManager);
    }

    @Test
    void 카테고리_정보_가져오기_서브쿼리_테스트() {
        QBoard board = QBoard.board;
        List<Tuple> results = query
                .select(board.category.id, board.category.id.count())
                .from(board)
                .groupBy(board.category.id)
                .fetch();
        for (Tuple result : results) {
            System.out.println("result = " + result);
        }
    }

    /*    "SELECT C.CATEGORY_ID AS id, C.PARENT_CATEGORY_ID AS parentCategoryId, CATEGORY_NAME AS name , COUNT AS categoryNum " +
            "FROM
            (SELECT CATEGORY_ID, COUNT(CATEGORY_ID) AS COUNT " +
            "FROM BOARD GROUP BY CATEGORY_ID) AS SQ " +
            "RIGHT OUTER JOIN CATEGORY AS C ON SQ.CATEGORY_ID = C.CATEGORY_ID " +
            "WHERE C.CATEGORY_NAME != 'total' " +
            "ORDER BY C.CATEGORY_ID ASC",*/

    @Test
    void 카테고리_정보_가져오기_쿼리_테스트() {
        QCategory category = QCategory.category;
        QBoard board = QBoard.board;
        List<CategoryInfoDto> results = query
                .select(Projections.constructor(CategoryInfoDto.class,
                        category.id, category.parentCategoryId, category.categoryName, category.count()))
                .from(category)
                .join(board)
                .on(category.id.eq(board.category.id))
                .groupBy(category.id)
                .fetch();
        for (CategoryInfoDto result : results) {
            System.out.println("result = " + result.toString());
        }
    }
}