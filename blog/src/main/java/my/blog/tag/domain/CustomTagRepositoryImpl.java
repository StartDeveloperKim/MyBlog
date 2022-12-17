package my.blog.tag.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomTagRepositoryImpl implements CustomTagRepository{

    private final JPAQueryFactory query;

    public CustomTagRepositoryImpl(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Tag> findTags(List<String> tags) {
        QTag tag = QTag.tag;
        return query.selectFrom(tag)
                .where(tag.tagName.in(tags))
                .fetch();
    }
}
