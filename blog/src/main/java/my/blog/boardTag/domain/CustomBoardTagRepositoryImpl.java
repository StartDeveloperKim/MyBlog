package my.blog.boardTag.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import my.blog.tag.domain.QTag;
import my.blog.tag.dto.TagCountResponse;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomBoardTagRepositoryImpl implements CustomBoardTagRepository{

    private final JPAQueryFactory query;

    public CustomBoardTagRepositoryImpl(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<TagCountResponse> findTagCountDtoOrderByCount(Long limit) {
        QBoardTag boardTag = QBoardTag.boardTag;
        QTag tag = QTag.tag;
        return query
                .select(Projections.constructor(TagCountResponse.class,
                        tag.tagName, tag.count()))
                .from(boardTag)
                .join(tag)
                .on(boardTag.tag.id.eq(tag.id))
                .groupBy(tag.id)
                .orderBy(tag.count().desc())
                .offset(0).limit(limit)
                .fetch();
    }
}
