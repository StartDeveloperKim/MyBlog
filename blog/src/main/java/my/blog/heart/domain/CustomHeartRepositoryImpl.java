package my.blog.heart.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomHeartRepositoryImpl implements CustomHeartRepository{

    private final JPAQueryFactory query;

    public CustomHeartRepositoryImpl(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }


    @Override
    public boolean existHeart(Long boardId, Long userId) {
        QHeart heart = QHeart.heart;

        Integer fetchOne = query.selectOne()
                .from(heart)
                .where(heart.board.id.eq(boardId).and(heart.user.id.eq(userId)))
                .fetchOne();

        return fetchOne != null; // 해당되는 좋아요가 있다면 true 반환ㄴ
    }
}
