package my.blog.board.domain.image;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitHubAPITest {

    String gitToken=""; // 테스트 통과

    @Test
    void 깃허브_연동_테스트() {
        try {
            GitHub gitHub = new GitHubBuilder().withOAuthToken(gitToken).build();
            gitHub.checkApiUrlValidity();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}