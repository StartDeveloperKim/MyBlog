package my.blog.board.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CustomBoardRepositoryImpl implements CustomBoardRepository{

    private final JPAQueryFactory query;

    public CustomBoardRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Board> searchBoardByTitle(String word, Pageable pageable) {
        QBoard board = QBoard.board;
        return query
                .select(board)
                .from(board)
                .where(board.title.contains(word))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}
