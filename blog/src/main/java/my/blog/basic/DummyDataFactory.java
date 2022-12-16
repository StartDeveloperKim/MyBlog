package my.blog.basic;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.comments.domain.Comments;
import my.blog.comments.domain.CommentsRepository;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/*@RequiredArgsConstructor
@Component*/
public class DummyDataFactory /*implements CommandLineRunner*/ {
    /*private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;
    private final CommentsRepository commentsRepository;


    private void saveChildComments(List<User> users, List<Board> boards) {
        List<Comments> comments = commentsRepository.findAll();
        for (int i = 0; i < 500; i++) {
            int randomBoard = (int) (Math.random() * boards.size());
            int randomUser = (int) (Math.random() * users.size());
            int randomComment = (int) (Math.random() * comments.size());
            commentsRepository.save(Comments.newInstance(boards.get(randomBoard), users.get(randomUser), "반갑습니다ㅎㅎ", comments.get(randomComment).getId()));
        }
    }

    private void saveParentComments(List<User> users, List<Board> boards) {
        for (int i = 0; i < 1200; i++) {
            int randomBoard = (int) (Math.random() * boards.size());
            int randomUser = (int) (Math.random() * users.size());
            Comments savedComment = commentsRepository.save(Comments.newInstance(boards.get(randomBoard), users.get(randomUser), "안녕하세요!!", null));
            commentsRepository.save(Comments.newInstance(boards.get(randomBoard), users.get(randomUser), "반갑습니다.!!", savedComment.getId()));
        }
    }

    private List<BoardTag> getBoardTagData(List<Tag> tags, List<Board> boards) {
        List<BoardTag> boardTags = new ArrayList<>();
        int tagSize = tags.size();
        int boardSize = boards.size();
        for (int i = 0; i < 100; i++) {
            int randomBoard = (int) (Math.random() * boardSize);
            int randomTag = (int) (Math.random() * tagSize);
            boardTags.add(BoardTag.newInstance(boards.get((randomBoard)), tags.get(randomTag)));
        }
        return boardTags;
    }

    private List<Board> getBoardData(List<Category> categories, User user) {
        List<Board> boards = new ArrayList<>();
        int categorySize = categories.size();
        for (int i = 0; i < 200; i++) {
            int randomCategory = (int) (Math.random() * categorySize);
            Board board = Board.newInstance(user, categories.get(randomCategory), "테스트" + i, "테스트" + i + "입니다.", null);
            boards.add(board);
        }
        return boards;
    }

    private List<Tag> getTagData() {
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tags.add(Tag.newInstance("테스트" + i));
        }
        return tags;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<User> users = userRepository.findAll();
        // 게시글 더미 데이터
        //List<Category> categories = categoryRepository.findAll();
        //User user = userRepository.findById(1L).get();
        //boardRepository.saveAll(getBoardData(categories, user));

        //태그 더미 데이터 저장
        //List<Tag> tagData = getTagData();
        //tagRepository.saveAll(tagData);

        // 게시글에 태그 걸어주기
        List<Tag> tags = tagRepository.findAll();
        List<Board> boards = boardRepository.findAll();
        boardTagRepository.saveAll(getBoardTagData(tags, boards));

        //부모 댓글 생성
        saveParentComments(users, boards);
    }*/
}
