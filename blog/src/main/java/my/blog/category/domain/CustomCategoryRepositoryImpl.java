package my.blog.category.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import my.blog.board.domain.QBoard;
import my.blog.category.dto.response.CategoryInfoDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CustomCategoryRepositoryImpl implements CustomCategoryRepository{

    private final JPAQueryFactory query;

    public CustomCategoryRepositoryImpl(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<CategoryInfoDto> getCategoryInfoDto() {
        QCategory category = QCategory.category;
        QBoard board = QBoard.board;
        return query
                .select(Projections.constructor(CategoryInfoDto.class,
                        category.id, category.parentCategoryId, category.categoryName, board.count()))
                .from(category)
                .leftJoin(board)
                .on(category.id.eq(board.category.id))
                .groupBy(category.id)
                .where(category.categoryName.ne("total"))
                .orderBy(category.id.asc())
                .fetch();
    }
}
